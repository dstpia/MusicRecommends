package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongSongDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.repository.InMemorySongDao;
import com.ragnarock.musicrecommends.services.implementations.InMemorySongServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class InMemorySongServiceImplTest {

    private InMemorySongDao repository;
    private LongSongDtoMapper longSongDtoMapper;
    private ShortSongDtoMapper shortSongDtoMapper;
    private InMemorySongServiceImpl songService;

    @BeforeEach
    void setUp() {
        repository = mock(InMemorySongDao.class);
        longSongDtoMapper = mock(LongSongDtoMapper.class);
        shortSongDtoMapper = mock(ShortSongDtoMapper.class);
        songService = new InMemorySongServiceImpl(repository, longSongDtoMapper, shortSongDtoMapper);
    }

    @Test
    void testFindAll() {
        List<Song> songs = List.of(new Song());
        List<LongSongDto> dtoList = List.of(new LongSongDto());

        when(repository.findAll()).thenReturn(songs);
        when(longSongDtoMapper.mapToLongDtoList(songs)).thenReturn(dtoList);

        List<LongSongDto> result = songService.findAll();

        assertEquals(dtoList, result);
        verify(repository).findAll();
        verify(longSongDtoMapper).mapToLongDtoList(songs);
    }

    @Test
    void testFindByNameAndLyrics() {
        List<Song> songs = List.of(new Song());
        List<LongSongDto> dtoList = List.of(new LongSongDto());

        when(repository.findByNameAndLyrics("Song", "Lyrics")).thenReturn(songs);
        when(longSongDtoMapper.mapToLongDtoList(songs)).thenReturn(dtoList);

        List<LongSongDto> result = songService.findByNameAndLyrics("Song", "Lyrics");

        assertEquals(dtoList, result);
        verify(repository).findByNameAndLyrics("Song", "Lyrics");
        verify(longSongDtoMapper).mapToLongDtoList(songs);
    }

    @Test
    void testFindById() {
        Song song = new Song();
        LongSongDto dto = new LongSongDto();

        when(repository.findById(1L)).thenReturn(song);
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(dto);

        LongSongDto result = songService.findById(1L);

        assertEquals(dto, result);
        verify(repository).findById(1L);
        verify(longSongDtoMapper).mapToLongDto(song);
    }

    @Test
    void testSaveSong() {
        ShortSongDto shortDto = new ShortSongDto("","");
        Song song = new Song();
        LongSongDto longDto = new LongSongDto();

        when(shortSongDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(song);
        when(repository.saveSong(song)).thenReturn(song);
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(longDto);

        LongSongDto result = songService.saveSong(shortDto);

        assertEquals(longDto, result);
        verify(shortSongDtoMapper).mapToObjectFromShort(shortDto);
        verify(repository).saveSong(song);
        verify(longSongDtoMapper).mapToLongDto(song);
    }

    @Test
    void testUpdateSong() {
        ShortSongDto shortDto = new ShortSongDto("","");
        Song song = new Song();
        LongSongDto longDto = new LongSongDto();

        when(shortSongDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(song);
        when(repository.updateSong(song)).thenReturn(song);
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(longDto);

        LongSongDto result = songService.updateSong(shortDto);

        assertEquals(longDto, result);
        verify(shortSongDtoMapper).mapToObjectFromShort(shortDto);
        verify(repository).updateSong(song);
        verify(longSongDtoMapper).mapToLongDto(song);
    }

    @Test
    void testDeleteSong() {
        Song song = new Song();
        when(repository.findById(1L)).thenReturn(song);

        boolean result = songService.deleteSong(1L);

        assertTrue(result);
        verify(repository).findById(1L);
        verify(repository).deleteSong(song);
    }

    @Test
    void testFindByAlbumYearNotAvailable() {
        List<LongSongDto> result = songService.findByAlbumYear(2020L);
        assertTrue(result.isEmpty());
    }

    @Test
    void testFindByAlbumGenreNotAvailable() {
        List<LongSongDto> result = songService.findByAlbumGenre("rock");
        assertTrue(result.isEmpty());
    }

    @Test
    void testSaveSongsListNotAvailable() {
        List<ShortSongDto> input = List.of(new ShortSongDto("",""));
        List<LongSongDto> result = songService.saveSongsList(input);
        assertTrue(result.isEmpty());
    }
}

