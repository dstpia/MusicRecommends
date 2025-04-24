package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAuthorDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAuthorDtoMapper;
import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import com.ragnarock.musicrecommends.services.implementations.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AuthorServiceImplTest {

    private AuthorRepository repository;
    private LongAuthorDtoMapper longAuthorDtoMapper;
    private ShortAuthorDtoMapper shortAuthorDtoMapper;
    private AuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        repository = mock(AuthorRepository.class);
        longAuthorDtoMapper = mock(LongAuthorDtoMapper.class);
        shortAuthorDtoMapper = mock(ShortAuthorDtoMapper.class);
        authorService = new AuthorServiceImpl(repository, longAuthorDtoMapper, shortAuthorDtoMapper);
    }

    @Test
    void testFindAll() {
        List<Author> authors = List.of(new Author());
        List<LongAuthorDto> dtoList = List.of(new LongAuthorDto());

        when(repository.findAll()).thenReturn(authors);
        when(longAuthorDtoMapper.mapToLongDtoList(authors)).thenReturn(dtoList);

        List<LongAuthorDto> result = authorService.findAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
        verify(longAuthorDtoMapper).mapToLongDtoList(authors);
    }

    @Test
    void testFindById_NotInCache() {
        Long id = 1L;
        Author author = new Author();
        LongAuthorDto dto = new LongAuthorDto();
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(author));
        when(longAuthorDtoMapper.mapToLongDto(author)).thenReturn(dto);

        LongAuthorDto result = authorService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(repository).findById(id);
    }

    @Test
    void testFindById_NullIfNotFound() {
        Long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        LongAuthorDto result = authorService.findById(id);

        assertNull(result);
    }

    @Test
    void testFindByNameAndGenre_FromRepository() {
        String name = "test";
        String genre = "rock";

        List<Author> authors = List.of(new Author());
        List<LongAuthorDto> dtos = List.of(new LongAuthorDto());

        when(repository.findByNameAndGenre(name, genre.toLowerCase())).thenReturn(authors);
        when(longAuthorDtoMapper.mapToLongDtoList(authors)).thenReturn(dtos);

        List<LongAuthorDto> result = authorService.findByNameAndGenre(name, genre);

        assertEquals(1, result.size());
        verify(repository).findByNameAndGenre(name, genre.toLowerCase());
    }

    @Test
    void testSaveAuthor() {
        ShortAuthorDto shortDto = new ShortAuthorDto("","");
        Author author = new Author();
        LongAuthorDto longDto = new LongAuthorDto();
        longDto.setId(1L);

        when(shortAuthorDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(author);
        when(repository.save(author)).thenReturn(author);
        when(longAuthorDtoMapper.mapToLongDto(author)).thenReturn(longDto);

        LongAuthorDto result = authorService.saveAuthor(shortDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(author);
    }

    @Test
    void testSaveAuthorsList() {
        ShortAuthorDto shortDto = new ShortAuthorDto("","");
        Author author = new Author();
        LongAuthorDto longDto = new LongAuthorDto();
        longDto.setId(2L);

        when(shortAuthorDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(author);
        when(repository.save(author)).thenReturn(author);
        when(longAuthorDtoMapper.mapToLongDto(author)).thenReturn(longDto);

        List<LongAuthorDto> result = authorService.saveAuthorsList(List.of(shortDto));

        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getId());
    }

    @Test
    void testUpdateAuthor_WhenExists() {
        ShortAuthorDto shortDto = new ShortAuthorDto("","");
        shortDto.setId(3L);
        Author author = new Author();
        LongAuthorDto longDto = new LongAuthorDto();
        longDto.setId(3L);

        when(repository.existsById(3L)).thenReturn(true);
        when(shortAuthorDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(author);
        when(repository.save(author)).thenReturn(author);
        when(longAuthorDtoMapper.mapToLongDto(author)).thenReturn(longDto);

        LongAuthorDto result = authorService.updateAuthor(shortDto);

        assertNotNull(result);
        assertEquals(3L, result.getId());
    }

    @Test
    void testUpdateAuthor_WhenNotExists() {
        ShortAuthorDto shortDto = new ShortAuthorDto("","");
        shortDto.setId(4L);

        when(repository.existsById(4L)).thenReturn(false);

        LongAuthorDto result = authorService.updateAuthor(shortDto);

        assertNull(result);
    }

    @Test
    void testDeleteAuthor_WhenExists() {
        Long id = 5L;

        when(repository.existsById(id)).thenReturn(true);

        boolean result = authorService.deleteAuthor(id);

        assertTrue(result);
        verify(repository).deleteById(id);
    }

    @Test
    void testDeleteAuthor_WhenNotExists() {
        Long id = 6L;

        when(repository.existsById(id)).thenReturn(false);

        boolean result = authorService.deleteAuthor(id);

        assertFalse(result);
        verify(repository, never()).deleteById(id);
    }

    @Test
    void testStringFilter() {
        assertTrue(authorService.stringFilter("rock", "rock"));
        assertFalse(authorService.stringFilter("rock", "pop"));
        assertTrue(authorService.stringFilter("rock", null));
        assertFalse(authorService.stringFilter(null, "rock"));
        assertTrue(authorService.stringFilter(null, null));
    }

    @Test
    void findByNameAndGenre_returnsFromCache_whenAvailable() {
        LongAuthorDto cachedDto = new LongAuthorDto();
        cachedDto.setId(1L);
        cachedDto.setName("test");
        cachedDto.setGenre("test");

        Author author = new Author();
        author.setId(1L);
        author.setName("test");
        author.setGenre("test");

        when(repository.findByNameAndGenre("test", "test")).thenReturn(List.of(author));
        when(longAuthorDtoMapper.mapToLongDtoList(List.of(author))).thenReturn(List.of(cachedDto));

        List<LongAuthorDto> firstCall = authorService.findByNameAndGenre("test", "test");
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        assertEquals("test", firstCall.get(0).getName());
        assertEquals("test", firstCall.get(0).getGenre());
        assertEquals(1L, firstCall.get(0).getId());

        clearInvocations(repository);

        List<LongAuthorDto> secondCall = authorService.findByNameAndGenre("test", "test");
        assertNotNull(secondCall);
        assertEquals(1, secondCall.size());
        assertEquals("test", secondCall.get(0).getName());
        assertEquals("test", secondCall.get(0).getGenre());
        assertEquals(1L, secondCall.get(0).getId());
    }

    @Test
    void deleteAuthor_deleteFromCache_whenAvailable() {
        LongAuthorDto dto = new LongAuthorDto();
        dto.setId(1L);

        Author author = new Author();
        author.setId(1L);

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(longAuthorDtoMapper.mapToLongDto(author)).thenReturn(dto);

        LongAuthorDto firstCall = authorService.findById(1L);
        assertNotNull(firstCall);

        clearInvocations(repository);

        boolean secondCall = authorService.deleteAuthor(1L);
        assertTrue(secondCall);
        verify(repository, never()).findById(any());
    }

    @Test
    void findById_returnsFromCache_whenAvailable() {
        LongAuthorDto dto = new LongAuthorDto();
        dto.setId(1L);

        Author author = new Author();
        author.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(author));
        when(longAuthorDtoMapper.mapToLongDto(author)).thenReturn(dto);

        LongAuthorDto firstCall = authorService.findById(1L);
        assertNotNull(firstCall);

        clearInvocations(repository);

        LongAuthorDto secondCall = authorService.findById(1L);
        assertNotNull(secondCall);
        assertEquals(1L, secondCall.getId());
        verify(repository, never()).findById(any());
    }
}
