package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAlbumDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAlbumDtoMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.services.implementations.AlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AlbumServiceImplTest {

    private AlbumRepository repository;
    private LongAlbumDtoMapper longAlbumDtoMapper;
    private ShortAlbumDtoMapper shortAlbumDtoMapper;
    private AlbumServiceImpl albumService;

    @BeforeEach
    void setUp() {
        repository = mock(AlbumRepository.class);
        longAlbumDtoMapper = mock(LongAlbumDtoMapper.class);
        shortAlbumDtoMapper = mock(ShortAlbumDtoMapper.class);
        albumService = new AlbumServiceImpl(repository, longAlbumDtoMapper, shortAlbumDtoMapper);
    }

    @Test
    void findAll_returnsAllAlbums() {
        when(repository.findAll()).thenReturn(List.of());
        when(longAlbumDtoMapper.mapToLongDtoList(anyList())).thenReturn(List.of());
        List<LongAlbumDto> result = albumService.findAll();
        assertNotNull(result);
    }

    @Test
    void findAll_returnsNull_whenRepositoryReturnsNull() {
        when(repository.findAll()).thenReturn(null);
        when(longAlbumDtoMapper.mapToLongDtoList(null)).thenReturn(List.of());
        List<LongAlbumDto> result = albumService.findAll();
        assertNotNull(result);
    }

    @Test
    void findById_returnsNull_whenAlbumNotFound() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        LongAlbumDto result = albumService.findById(99L);
        assertNull(result);
    }

    @Test
    void findById_returnsFromCache_whenAvailable() {
        LongAlbumDto dto = new LongAlbumDto();
        dto.setId(1L);

        Album album = new Album();
        album.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(album));
        when(longAlbumDtoMapper.mapToLongDto(album)).thenReturn(dto);

        LongAlbumDto firstCall = albumService.findById(1L);
        assertNotNull(firstCall);

        clearInvocations(repository);

        LongAlbumDto secondCall = albumService.findById(1L);
        assertNotNull(secondCall);
        assertEquals(1L, secondCall.getId());
        verify(repository, never()).findById(any());
    }



    @Test
    void saveAlbumsList_returnsEmptyList_whenInputEmpty() {
        List<LongAlbumDto> result = albumService.saveAlbumsList(Collections.emptyList());
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByNameAndGenre_returnsEmpty_whenRepositoryReturnsEmpty() {
        when(repository.findByNameAndGenre("name", "genre")).thenReturn(List.of());
        when(longAlbumDtoMapper.mapToLongDtoList(List.of())).thenReturn(List.of());
        List<LongAlbumDto> result = albumService.findByNameAndGenre("name", "genre");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findByYear_returnsEmpty_whenNoAlbumsFound() {
        when(repository.findByYear(1999L)).thenReturn(List.of());
        when(longAlbumDtoMapper.mapToLongDtoList(List.of())).thenReturn(List.of());
        List<LongAlbumDto> result = albumService.findByYear(1999L);
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_returnsAlbum_whenPresent() {
        LongAlbumDto expected = new LongAlbumDto();
        expected.setId(1L);
        Album album = new Album();
        album.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(album));
        when(longAlbumDtoMapper.mapToLongDto(album)).thenReturn(expected);
        LongAlbumDto result = albumService.findById(1L);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void saveAlbum_savesAndReturnsAlbum() {
        ShortAlbumDto shortDto = new ShortAlbumDto("", "");
        Album albumEntity = new Album();
        albumEntity.setId(1L);
        LongAlbumDto longDto = new LongAlbumDto();
        longDto.setId(1L);

        when(shortAlbumDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(albumEntity);
        when(repository.save(albumEntity)).thenReturn(albumEntity);
        when(longAlbumDtoMapper.mapToLongDto(albumEntity)).thenReturn(longDto);

        LongAlbumDto result = albumService.saveAlbum(shortDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void updateAlbum_whenExists_updatesSuccessfully() {
        ShortAlbumDto shortDto = new ShortAlbumDto("", "");
        shortDto.setId(1L);
        Album albumEntity = new Album();
        albumEntity.setId(1L);
        LongAlbumDto longDto = new LongAlbumDto();
        longDto.setId(1L);

        when(repository.existsById(1L)).thenReturn(true);
        when(shortAlbumDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(albumEntity);
        when(repository.save(albumEntity)).thenReturn(albumEntity);
        when(longAlbumDtoMapper.mapToLongDto(albumEntity)).thenReturn(longDto);

        LongAlbumDto result = albumService.updateAlbum(shortDto);
        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void updateAlbum_whenNotExists_returnsNull() {
        ShortAlbumDto shortDto = new ShortAlbumDto("", "");
        shortDto.setId(2L);
        when(repository.existsById(2L)).thenReturn(false);
        LongAlbumDto result = albumService.updateAlbum(shortDto);
        assertNull(result);
    }

    @Test
    void deleteAlbum_whenExists_deletesSuccessfully() {
        when(repository.existsById(1L)).thenReturn(true);
        boolean result = albumService.deleteAlbum(1L);
        assertTrue(result);
        verify(repository).deleteById(1L);
    }

    @Test
    void deleteAlbum_whenNotExists_returnsFalse() {
        when(repository.existsById(2L)).thenReturn(false);
        boolean result = albumService.deleteAlbum(2L);
        assertFalse(result);
    }

    @Test
    void saveAlbumsList_savesMultipleAlbums() {
        ShortAlbumDto dto1 = new ShortAlbumDto("", "");
        dto1.setId(1L);
        ShortAlbumDto dto2 = new ShortAlbumDto("", "");
        dto2.setId(2L);

        Album album1 = new Album();
        album1.setId(1L);
        Album album2 = new Album();
        album2.setId(2L);

        LongAlbumDto longDto1 = new LongAlbumDto();
        longDto1.setId(1L);
        LongAlbumDto longDto2 = new LongAlbumDto();
        longDto2.setId(2L);

        when(shortAlbumDtoMapper.mapToObjectFromShort(dto1)).thenReturn(album1);
        when(shortAlbumDtoMapper.mapToObjectFromShort(dto2)).thenReturn(album2);
        when(repository.save(album1)).thenReturn(album1);
        when(repository.save(album2)).thenReturn(album2);
        when(longAlbumDtoMapper.mapToLongDto(album1)).thenReturn(longDto1);
        when(longAlbumDtoMapper.mapToLongDto(album2)).thenReturn(longDto2);

        List<LongAlbumDto> result = albumService.saveAlbumsList(List.of(dto1, dto2));

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals(2L, result.get(1).getId());
    }

    @Test
    void findByNameAndGenre_returnsFilteredAlbums() {
        Album album = new Album();
        album.setId(1L);
        album.setName("test");
        album.setGenre("rock");
        LongAlbumDto dto = new LongAlbumDto();
        dto.setId(1L);

        when(repository.findByNameAndGenre("test", "rock")).thenReturn(List.of(album));
        when(longAlbumDtoMapper.mapToLongDtoList(List.of(album))).thenReturn(List.of(dto));

        List<LongAlbumDto> result = albumService.findByNameAndGenre("test", "rock");

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void findByYear_returnsAlbumsByYear() {
        Album album = new Album();
        album.setId(1L);
        album.setYear(2000L);
        LongAlbumDto dto = new LongAlbumDto();
        dto.setId(1L);

        when(repository.findByYear(2000L)).thenReturn(List.of(album));
        when(longAlbumDtoMapper.mapToLongDtoList(List.of(album))).thenReturn(List.of(dto));

        List<LongAlbumDto> result = albumService.findByYear(2000L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
    }

    @Test
    void findByNameAndGenre_returnsFromCache_whenAvailable() {
        LongAlbumDto cachedDto = new LongAlbumDto();
        cachedDto.setId(1L);
        cachedDto.setName("test");
        cachedDto.setGenre("test");

        Album album = new Album();
        album.setId(1L);
        album.setName("test");
        album.setGenre("test");

        when(repository.findByNameAndGenre("test", "test")).thenReturn(List.of(album));
        when(longAlbumDtoMapper.mapToLongDtoList(List.of(album))).thenReturn(List.of(cachedDto));

        List<LongAlbumDto> firstCall = albumService.findByNameAndGenre("test", "test");
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        assertEquals("test", firstCall.get(0).getName());
        assertEquals("test", firstCall.get(0).getGenre());
        assertEquals(1L, firstCall.get(0).getId());

        clearInvocations(repository);

        List<LongAlbumDto> secondCall = albumService.findByNameAndGenre("test", "test");
        assertNotNull(secondCall);
        assertEquals(1, secondCall.size());
        assertEquals("test", secondCall.get(0).getName());
        assertEquals("test", secondCall.get(0).getGenre());
        assertEquals(1L, secondCall.get(0).getId());
    }

    @Test
    void findByYear_returnsFromCache_whenAvailable() {
        LongAlbumDto cachedDto = new LongAlbumDto();
        cachedDto.setId(1L);
        cachedDto.setYear(2000L);

        Album album = new Album();
        album.setId(1L);
        album.setYear(2000L);

        when(repository.findByYear(2000L)).thenReturn(List.of(album));
        when(longAlbumDtoMapper.mapToLongDtoList(List.of(album))).thenReturn(List.of(cachedDto));

        List<LongAlbumDto> firstCall = albumService.findByYear(2000L);
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        assertEquals(1L, firstCall.get(0).getId());
        assertEquals(2000L, firstCall.get(0).getYear());

        clearInvocations(repository);

        List<LongAlbumDto> secondCall = albumService.findByYear(2000L);
        assertNotNull(secondCall);
        assertEquals(1, secondCall.size());
        assertEquals(1L, secondCall.get(0).getId());
        assertEquals(2000L, secondCall.get(0).getYear());
    }

    @Test
    void deleteAlbum_deleteFromCache_whenAvailable() {
        LongAlbumDto dto = new LongAlbumDto();
        dto.setId(1L);

        Album album = new Album();
        album.setId(1L);

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(album));
        when(longAlbumDtoMapper.mapToLongDto(album)).thenReturn(dto);

        LongAlbumDto firstCall = albumService.findById(1L);
        assertNotNull(firstCall);

        clearInvocations(repository);

        boolean secondCall = albumService.deleteAlbum(1L);
        assertTrue(secondCall);
        verify(repository, never()).findById(any());
    }

    @Test
    void stringFilter_returnsTrueWhenFilterNull() {
        assertTrue(albumService.stringFilter("some", null));
    }

    @Test
    void stringFilter_returnsTrueWhenMatch() {
        assertTrue(albumService.stringFilter("rock", "rock"));
    }

    @Test
    void stringFilter_returnsFalseWhenMismatch() {
        assertFalse(albumService.stringFilter("rock", "pop"));
    }

    @Test
    void stringFilter_returnsFalseWhenCompareNull() {
        assertFalse(albumService.stringFilter(null, "rock"));
    }
}
