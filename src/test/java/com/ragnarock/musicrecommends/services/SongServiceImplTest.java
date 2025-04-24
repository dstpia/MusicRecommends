package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongSongDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.repository.SongRepository;
import com.ragnarock.musicrecommends.services.implementations.SongServiceImpl;
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

class SongServiceImplTest {

    private SongRepository repository;
    private LongSongDtoMapper longSongDtoMapper;
    private ShortSongDtoMapper shortSongDtoMapper;
    private SongServiceImpl songService;

    @BeforeEach
    void setUp() {
        repository = mock(SongRepository.class);
        longSongDtoMapper = mock(LongSongDtoMapper.class);
        shortSongDtoMapper = mock(ShortSongDtoMapper.class);
        songService = new SongServiceImpl(repository, longSongDtoMapper, shortSongDtoMapper);
    }

    @Test
    void testFindAll() {
        List<Song> songs = List.of(new Song());
        List<LongSongDto> dtoList = List.of(new LongSongDto());

        when(repository.findAll()).thenReturn(songs);
        when(longSongDtoMapper.mapToLongDtoList(songs)).thenReturn(dtoList);

        List<LongSongDto> result = songService.findAll();

        assertEquals(1, result.size());
        verify(repository).findAll();
        verify(longSongDtoMapper).mapToLongDtoList(songs);
    }

    @Test
    void testFindById_NotInCache() {
        Long id = 1L;
        Song song = new Song();
        LongSongDto dto = new LongSongDto();
        dto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(song));
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(dto);

        LongSongDto result = songService.findById(id);

        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(repository).findById(id);
    }

    @Test
    void testFindById_NullIfNotFound() {
        Long id = 2L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        LongSongDto result = songService.findById(id);

        assertNull(result);
    }

    @Test
    void testFindByNameAndLyrics_FromRepository() {
        String name = "Ballad";

        List<Song> songs = List.of(new Song());
        List<LongSongDto> dtoList = List.of(new LongSongDto());

        when(repository.findByNameAndLyrics(name, "Love")).thenReturn(songs);
        when(longSongDtoMapper.mapToLongDtoList(songs)).thenReturn(dtoList);

        List<LongSongDto> result = songService.findByNameAndLyrics(name, "Love");

        assertEquals(1, result.size());
        verify(repository).findByNameAndLyrics(name, "Love");
    }

    @Test
    void testFindByAlbumYear_FromRepository() {
        Long year = 2000L;
        List<Song> songs = List.of(new Song());
        List<LongSongDto> dtoList = List.of(new LongSongDto());

        when(repository.findByAlbumYear(year)).thenReturn(songs);
        when(longSongDtoMapper.mapToLongDtoList(songs)).thenReturn(dtoList);

        List<LongSongDto> result = songService.findByAlbumYear(year);

        assertEquals(1, result.size());
        verify(repository).findByAlbumYear(year);
    }

    @Test
    void testFindByAlbumGenre_FromRepository() {
        List<Song> songs = List.of(new Song());
        List<LongSongDto> dtoList = List.of(new LongSongDto());

        when(repository.findByAlbumGenre("rock")).thenReturn(songs);
        when(longSongDtoMapper.mapToLongDtoList(songs)).thenReturn(dtoList);

        List<LongSongDto> result = songService.findByAlbumGenre("Rock");

        assertEquals(1, result.size());
        verify(repository).findByAlbumGenre("rock");
    }

    @Test
    void testSaveSong() {
        ShortSongDto shortDto = new ShortSongDto("","");
        Song song = new Song();
        LongSongDto longDto = new LongSongDto();
        longDto.setId(1L);

        when(shortSongDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(song);
        when(repository.save(song)).thenReturn(song);
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(longDto);

        LongSongDto result = songService.saveSong(shortDto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testSaveSongsList() {
        ShortSongDto shortDto = new ShortSongDto("","");
        Song song = new Song();
        LongSongDto longDto = new LongSongDto();
        longDto.setId(2L);

        when(shortSongDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(song);
        when(repository.save(song)).thenReturn(song);
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(longDto);

        List<LongSongDto> result = songService.saveSongsList(List.of(shortDto));

        assertEquals(1, result.size());
        assertEquals(2L, result.get(0).getId());
    }

    @Test
    void testUpdateSong_WhenExists() {
        ShortSongDto shortDto = new ShortSongDto("","");
        shortDto.setId(3L);
        Song song = new Song();
        LongSongDto longDto = new LongSongDto();
        longDto.setId(3L);

        when(repository.existsById(3L)).thenReturn(true);
        when(shortSongDtoMapper.mapToObjectFromShort(shortDto)).thenReturn(song);
        when(repository.save(song)).thenReturn(song);
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(longDto);

        LongSongDto result = songService.updateSong(shortDto);

        assertNotNull(result);
        assertEquals(3L, result.getId());
    }

    @Test
    void testUpdateSong_WhenNotExists() {
        ShortSongDto shortDto = new ShortSongDto("","");
        shortDto.setId(4L);

        when(repository.existsById(4L)).thenReturn(false);

        LongSongDto result = songService.updateSong(shortDto);

        assertNull(result);
    }

    @Test
    void testDeleteSong_WhenExists() {
        Long id = 5L;

        when(repository.existsById(id)).thenReturn(true);

        boolean result = songService.deleteSong(id);

        assertTrue(result);
        verify(repository).deleteById(id);
    }

    @Test
    void testDeleteSong_WhenNotExists() {
        Long id = 6L;

        when(repository.existsById(id)).thenReturn(false);

        boolean result = songService.deleteSong(id);

        assertFalse(result);
        verify(repository, never()).deleteById(id);
    }

    @Test
    void testStringFilter() {
        assertTrue(songService.stringFilter("lyric", "lyric"));
        assertFalse(songService.stringFilter("lyric", "poem"));
        assertTrue(songService.stringFilter("lyric", null));
        assertFalse(songService.stringFilter(null, "lyric"));
        assertTrue(songService.stringFilter(null, null));
    }

    @Test
    void findByNameAndLyrics_returnsFromCache_whenAvailable() {
        LongSongDto cachedDto = new LongSongDto();
        cachedDto.setId(1L);
        cachedDto.setName("test");
        cachedDto.setLyrics("Test");

        Song song = new Song();
        song.setId(1L);
        song.setName("test");
        song.setLyrics("Test");

        when(repository.findByNameAndLyrics("test", "Test")).thenReturn(List.of(song));
        when(longSongDtoMapper.mapToLongDtoList(List.of(song))).thenReturn(List.of(cachedDto));

        List<LongSongDto> firstCall = songService.findByNameAndLyrics("test", "Test");
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        assertEquals("test", firstCall.get(0).getName());
        assertEquals("Test", firstCall.get(0).getLyrics());
        assertEquals(1L, firstCall.get(0).getId());

        clearInvocations(repository);

        List<LongSongDto> secondCall = songService.findByNameAndLyrics("test", "Test");
        assertNotNull(secondCall);
        assertEquals(1, secondCall.size());
        assertEquals("test", secondCall.get(0).getName());
        assertEquals("Test", secondCall.get(0).getLyrics());
        assertEquals(1L, secondCall.get(0).getId());
    }

    @Test
    void findByNameAndLyrics_returnsFromCache_whenEnteredNameAndLyricsIsNull() {
        LongSongDto cachedDto = new LongSongDto();
        cachedDto.setId(1L);
        cachedDto.setName("test");
        cachedDto.setLyrics("Test");

        Song song = new Song();
        song.setId(1L);
        song.setName("test");
        song.setLyrics("Test");

        when(repository.findByNameAndLyrics(null, null)).thenReturn(List.of(song));
        when(longSongDtoMapper.mapToLongDtoList(List.of(song))).thenReturn(List.of(cachedDto));

        List<LongSongDto> firstCall = songService.findByNameAndLyrics(null, null);
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        assertEquals("test", firstCall.get(0).getName());
        assertEquals("Test", firstCall.get(0).getLyrics());
        assertEquals(1L, firstCall.get(0).getId());

        clearInvocations(repository);

        List<LongSongDto> secondCall = songService.findByNameAndLyrics(null, null);
        assertNotNull(secondCall);
        assertEquals(1, secondCall.size());
        assertEquals("test", secondCall.get(0).getName());
        assertEquals("Test", secondCall.get(0).getLyrics());
        assertEquals(1L, secondCall.get(0).getId());
    }

    @Test
    void findByAlbumGenre_returnsFromCache_whenAvailable() {
        ShortAlbumDto albumDto = new ShortAlbumDto("","");
        albumDto.setId(1L);
        albumDto.setGenre("test");

        LongSongDto cachedDto = new LongSongDto();
        cachedDto.setId(1L);
        cachedDto.setAlbum(albumDto);

        Album album = new Album();
        album.setId(1L);
        album.setGenre("test");

        Song song = new Song();
        song.setId(1L);
        song.setAlbum(album);

        when(repository.findByAlbumGenre("test")).thenReturn(List.of(song));
        when(longSongDtoMapper.mapToLongDtoList(List.of(song))).thenReturn(List.of(cachedDto));

        List<LongSongDto> firstCall = songService.findByAlbumGenre("test");
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        assertEquals(1L, firstCall.get(0).getId());
        assertNotNull(firstCall.get(0).getAlbum());
        assertEquals(1L, firstCall.get(0).getAlbum().getId());
        assertEquals("test", firstCall.get(0).getAlbum().getGenre());

        clearInvocations(repository);

        List<LongSongDto> secondCall = songService.findByAlbumGenre("test");
        assertNotNull(secondCall);
        assertEquals(1, secondCall.size());
        assertEquals(1L, secondCall.get(0).getId());
        assertNotNull(secondCall.get(0).getAlbum());
        assertEquals(1L, secondCall.get(0).getAlbum().getId());
        assertEquals("test", secondCall.get(0).getAlbum().getGenre());
    }

    @Test
    void findByAlbumGenre_returnsFromCache_whenEnteredAlbumGenreIsNull() {
        ShortAlbumDto albumDto = new ShortAlbumDto("","");
        albumDto.setId(1L);
        albumDto.setGenre("test");

        LongSongDto cachedDto = new LongSongDto();
        cachedDto.setId(1L);
        cachedDto.setAlbum(albumDto);

        Album album = new Album();
        album.setId(1L);
        album.setGenre("test");

        Song song = new Song();
        song.setId(1L);
        song.setAlbum(album);

        when(repository.findByAlbumGenre(null)).thenReturn(List.of(song));
        when(longSongDtoMapper.mapToLongDtoList(List.of(song))).thenReturn(List.of(cachedDto));

        List<LongSongDto> firstCall = songService.findByAlbumGenre(null);
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        assertEquals(1L, firstCall.get(0).getId());
        assertNotNull(firstCall.get(0).getAlbum());
        assertEquals(1L, firstCall.get(0).getAlbum().getId());
        assertNotNull(firstCall.get(0).getAlbum().getGenre());

        clearInvocations(repository);

        List<LongSongDto> secondCall = songService.findByAlbumGenre(null);
        assertNotNull(secondCall);
        assertEquals(1, secondCall.size());
        assertEquals(1L, secondCall.get(0).getId());
        assertNotNull(secondCall.get(0).getAlbum());
        assertEquals(1L, secondCall.get(0).getAlbum().getId());
        assertNotNull(secondCall.get(0).getAlbum().getGenre());
    }

    @Test
    void findByAlbumYear_returnsFromCache_whenAvailable() {
        ShortAlbumDto albumDto = new ShortAlbumDto("","");
        albumDto.setId(1L);
        albumDto.setYear(2000L);

        LongSongDto cachedDto = new LongSongDto();
        cachedDto.setId(1L);
        cachedDto.setAlbum(albumDto);

        Album album = new Album();
        album.setId(1L);
        album.setYear(2000L);

        Song song = new Song();
        song.setId(1L);
        song.setAlbum(album);

        when(repository.findByAlbumYear(2000L)).thenReturn(List.of(song));
        when(longSongDtoMapper.mapToLongDtoList(List.of(song))).thenReturn(List.of(cachedDto));

        List<LongSongDto> firstCall = songService.findByAlbumYear(2000L);
        assertNotNull(firstCall);
        assertEquals(1, firstCall.size());
        assertEquals(1L, firstCall.get(0).getId());
        assertNotNull(firstCall.get(0).getAlbum());
        assertEquals(1L, firstCall.get(0).getAlbum().getId());
        assertEquals(2000L, firstCall.get(0).getAlbum().getYear());

        clearInvocations(repository);

        List<LongSongDto> secondCall = songService.findByAlbumYear(2000L);
        assertNotNull(secondCall);
        assertEquals(1, secondCall.size());
        assertEquals(1L, secondCall.get(0).getId());
        assertNotNull(secondCall.get(0).getAlbum());
        assertEquals(1L, secondCall.get(0).getAlbum().getId());
        assertEquals(2000L, secondCall.get(0).getAlbum().getYear());
    }

    @Test
    void deleteSong_deleteFromCache_whenAvailable() {
        LongSongDto dto = new LongSongDto();
        dto.setId(1L);

        Song song = new Song();
        song.setId(1L);

        when(repository.existsById(1L)).thenReturn(true);
        when(repository.findById(1L)).thenReturn(Optional.of(song));
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(dto);

        LongSongDto firstCall = songService.findById(1L);
        assertNotNull(firstCall);

        clearInvocations(repository);

        boolean secondCall = songService.deleteSong(1L);
        assertTrue(secondCall);
        verify(repository, never()).findById(any());
    }

    @Test
    void findById_returnsFromCache_whenAvailable() {
        LongSongDto dto = new LongSongDto();
        dto.setId(1L);

        Song song = new Song();
        song.setId(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(song));
        when(longSongDtoMapper.mapToLongDto(song)).thenReturn(dto);

        LongSongDto firstCall = songService.findById(1L);
        assertNotNull(firstCall);

        clearInvocations(repository);

        LongSongDto secondCall = songService.findById(1L);
        assertNotNull(secondCall);
        assertEquals(1L, secondCall.getId());
        verify(repository, never()).findById(any());
    }
}
