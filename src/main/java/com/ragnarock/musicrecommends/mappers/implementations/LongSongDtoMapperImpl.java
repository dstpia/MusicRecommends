package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongSongDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAlbumDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAuthorDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import com.ragnarock.musicrecommends.repository.SongRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LongSongDtoMapperImpl implements LongSongDtoMapper {
    private final AlbumRepository albumRepository;
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;
    private final ShortAlbumDtoMapper shortAlbumDtoMapper;
    private final ShortAuthorDtoMapper shortAuthorDtoMapper;
    private final ShortSongDtoMapper shortSongDtoMapper;

    @Override
    public LongSongDto mapToLongDto(Song song) {
        LongSongDto longSongDto = new LongSongDto();
        if (song != null) {
            longSongDto.setId(song.getId());
            longSongDto.setName(song.getName());
            longSongDto.setLyrics(song.getLyrics());
            longSongDto.setAlbum(shortAlbumDtoMapper.mapToShortDto(song.getAlbum()));
            longSongDto.setAuthors(shortAuthorDtoMapper.mapToShortDtoList(song.getAuthors()));
        }
        return longSongDto;
    }

    @Override
    public Song mapToObjectFromLong(LongSongDto longSongDto) {
        Song song = new Song();
        if (longSongDto != null) {
            song.setId(longSongDto.getId());
            song.setName(longSongDto.getName());
            song.setLyrics(longSongDto.getLyrics());
            ShortSongDto shortSongDto = shortSongDtoMapper.mapToShortDto(songRepository
                    .getReferenceById(longSongDto.getId()));
            if (shortSongDto.getAlbumId() != null) {
                song.setAlbum(albumRepository.getReferenceById(shortSongDto.getAlbumId()));
            }
            if (shortSongDto.getAuthorsId() != null) {
                song.setAuthors(authorRepository.findAllById(shortSongDto.getAuthorsId()));
            }
        }
        return song;
    }

    @Override
    public List<LongSongDto> mapToLongDtoList(List<Song> songs) {
        List<LongSongDto> songsDto = new ArrayList<>();
        if (songs != null) {
            songs.forEach(song -> songsDto.add(mapToLongDto(song)));
        }
        return songsDto;
    }

    @Override
    public List<Song> mapToObjectListFromLong(List<LongSongDto> songsDto) {
        List<Song> songs = new ArrayList<>();
        if (songsDto != null) {
            songsDto.forEach(songDto -> songs.add(mapToObjectFromLong(songDto)));
        }
        return songs;
    }
}
