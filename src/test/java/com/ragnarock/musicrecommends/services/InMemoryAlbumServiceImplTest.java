package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAlbumDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAlbumDtoMapper;
import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.repository.InMemoryAlbumDao;
import com.ragnarock.musicrecommends.services.implementations.InMemoryAlbumServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InMemoryAlbumServiceImplTest {

    private InMemoryAlbumDao repository;
    private LongAlbumDtoMapper longAlbumDtoMapper;
    private ShortAlbumDtoMapper shortAlbumDtoMapper;
    private InMemoryAlbumServiceImpl albumService;

    @BeforeEach
    void setUp() {
        repository = mock(InMemoryAlbumDao.class);
        longAlbumDtoMapper = mock(LongAlbumDtoMapper.class);
        shortAlbumDtoMapper = mock(ShortAlbumDtoMapper.class);
        albumService = new InMemoryAlbumServiceImpl(repository, longAlbumDtoMapper, shortAlbumDtoMapper);
    }

    @Test
    void shouldFindAllAlbums() {
        List<Album> albums = List.of(new Album());
        List<LongAlbumDto> albumDtos = List.of(new LongAlbumDto());

        when(repository.findAll()).thenReturn(albums);
        when(longAlbumDtoMapper.mapToLongDtoList(albums)).thenReturn(albumDtos);

        List<LongAlbumDto> result = albumService.findAll();

        assertEquals(albumDtos, result);
        verify(repository).findAll();
        verify(longAlbumDtoMapper).mapToLongDtoList(albums);
    }

    @Test
    void shouldFindByNameAndGenre() {
        List<Album> albums = List.of(new Album());
        List<LongAlbumDto> dtoList = List.of(new LongAlbumDto());

        when(repository.findByNameAndGenre("Test", "Rock")).thenReturn(albums);
        when(longAlbumDtoMapper.mapToLongDtoList(albums)).thenReturn(dtoList);

        List<LongAlbumDto> result = albumService.findByNameAndGenre("Test", "Rock");

        assertEquals(dtoList, result);
    }

    @Test
    void shouldFindByYear() {
        List<Album> albums = List.of(new Album());
        List<LongAlbumDto> dtoList = List.of(new LongAlbumDto());

        when(repository.findByYear(2020L)).thenReturn(albums);
        when(longAlbumDtoMapper.mapToLongDtoList(albums)).thenReturn(dtoList);

        List<LongAlbumDto> result = albumService.findByYear(2020L);

        assertEquals(dtoList, result);
    }

    @Test
    void shouldFindById() {
        Album album = new Album();
        LongAlbumDto dto = new LongAlbumDto();

        when(repository.findById(1L)).thenReturn(album);
        when(longAlbumDtoMapper.mapToLongDto(album)).thenReturn(dto);

        LongAlbumDto result = albumService.findById(1L);

        assertEquals(dto, result);
    }

    @Test
    void shouldSaveAlbum() {
        ShortAlbumDto shortDto = new ShortAlbumDto("","");
        Album album = new Album();
        Album savedAlbum = new Album();
        LongAlbumDto longDto = new LongAlbumDto();

        when(shortAlbumDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(album);
        when(repository.saveAlbum(album)).thenReturn(savedAlbum);
        when(longAlbumDtoMapper.mapToLongDto(savedAlbum)).thenReturn(longDto);

        LongAlbumDto result = albumService.saveAlbum(shortDto);

        assertEquals(longDto, result);
    }

    @Test
    void shouldReturnEmptyListForSaveAlbumsList() {
        List<LongAlbumDto> result = albumService.saveAlbumsList(List.of());
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldUpdateAlbum() {
        ShortAlbumDto shortDto = new ShortAlbumDto("","");
        Album album = new Album();
        Album updatedAlbum = new Album();
        LongAlbumDto longDto = new LongAlbumDto();

        when(shortAlbumDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(album);
        when(repository.updateAlbum(album)).thenReturn(updatedAlbum);
        when(longAlbumDtoMapper.mapToLongDto(updatedAlbum)).thenReturn(longDto);

        LongAlbumDto result = albumService.updateAlbum(shortDto);

        assertEquals(longDto, result);
    }

    @Test
    void shouldDeleteAlbum() {
        Album album = new Album();
        when(repository.findById(1L)).thenReturn(album);

        boolean result = albumService.deleteAlbum(1L);

        assertTrue(result);
        verify(repository).deleteAlbum(album);
    }
}
