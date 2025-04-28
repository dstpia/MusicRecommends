package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAuthorDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAuthorDtoMapper;
import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.repository.InMemoryAuthorDao;
import com.ragnarock.musicrecommends.services.implementations.InMemoryAuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class InMemoryAuthorServiceImplTest {

    private InMemoryAuthorDao repository;
    private LongAuthorDtoMapper longAuthorDtoMapper;
    private ShortAuthorDtoMapper shortAuthorDtoMapper;
    private InMemoryAuthorServiceImpl authorService;

    @BeforeEach
    void setUp() {
        repository = mock(InMemoryAuthorDao.class);
        longAuthorDtoMapper = mock(LongAuthorDtoMapper.class);
        shortAuthorDtoMapper = mock(ShortAuthorDtoMapper.class);
        authorService = new InMemoryAuthorServiceImpl(repository, longAuthorDtoMapper, shortAuthorDtoMapper);
    }

    @Test
    void shouldFindAllAuthors() {
        List<Author> authors = List.of(new Author());
        List<LongAuthorDto> dtoList = List.of(new LongAuthorDto());

        when(repository.findAll()).thenReturn(authors);
        when(longAuthorDtoMapper.mapToLongDtoList(authors)).thenReturn(dtoList);

        List<LongAuthorDto> result = authorService.findAll();

        assertEquals(dtoList, result);
        verify(repository).findAll();
        verify(longAuthorDtoMapper).mapToLongDtoList(authors);
    }

    @Test
    void shouldFindByNameAndGenre() {
        List<Author> authors = List.of(new Author());
        List<LongAuthorDto> dtoList = List.of(new LongAuthorDto());

        when(repository.findByNameAndGenre("John", "Jazz")).thenReturn(authors);
        when(longAuthorDtoMapper.mapToLongDtoList(authors)).thenReturn(dtoList);

        List<LongAuthorDto> result = authorService.findByNameAndGenre("John", "Jazz");

        assertEquals(dtoList, result);
    }

    @Test
    void shouldFindById() {
        Author author = new Author();
        LongAuthorDto dto = new LongAuthorDto();

        when(repository.findById(1L)).thenReturn(author);
        when(longAuthorDtoMapper.mapToLongDto(author)).thenReturn(dto);

        LongAuthorDto result = authorService.findById(1L);

        assertEquals(dto, result);
    }

    @Test
    void shouldSaveAuthor() {
        ShortAuthorDto shortDto = new ShortAuthorDto("","");
        Author author = new Author();
        Author savedAuthor = new Author();
        LongAuthorDto longDto = new LongAuthorDto();

        when(shortAuthorDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(author);
        when(repository.saveAuthor(author)).thenReturn(savedAuthor);
        when(longAuthorDtoMapper.mapToLongDto(savedAuthor)).thenReturn(longDto);

        LongAuthorDto result = authorService.saveAuthor(shortDto);

        assertEquals(longDto, result);
    }

    @Test
    void shouldReturnEmptyListForSaveAuthorsList() {
        List<LongAuthorDto> result = authorService.saveAuthorsList(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateAuthor() {
        ShortAuthorDto shortDto = new ShortAuthorDto("","");
        Author author = new Author();
        Author updatedAuthor = new Author();
        LongAuthorDto longDto = new LongAuthorDto();

        when(shortAuthorDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(author);
        when(repository.updateAuthor(author)).thenReturn(updatedAuthor);
        when(longAuthorDtoMapper.mapToLongDto(updatedAuthor)).thenReturn(longDto);

        LongAuthorDto result = authorService.updateAuthor(shortDto);

        assertEquals(longDto, result);
    }

    @Test
    void shouldDeleteAuthor() {
        Author author = new Author();

        when(repository.findById(1L)).thenReturn(author);

        boolean result = authorService.deleteAuthor(1L);

        assertTrue(result);
        verify(repository).deleteAuthor(author);
    }
}

