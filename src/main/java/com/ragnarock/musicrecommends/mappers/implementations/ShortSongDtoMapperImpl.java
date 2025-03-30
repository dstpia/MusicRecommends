package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShortSongDtoMapperImpl implements ShortSongDtoMapper {
    private final AlbumRepository albumRepository;
    private final AuthorRepository authorRepository;

    @Override
    public ShortSongDto mapToShortDto(Song song) {
        ShortSongDto shortSongDto = new ShortSongDto();
        shortSongDto.setId(song.getId());
        shortSongDto.setName(song.getName());
        shortSongDto.setLyrics(song.getLyrics());
        if (song.getAlbum() != null) {
            shortSongDto.setAlbumId(song.getAlbum().getId());
        }
        if (song.getAuthors() != null) {
            shortSongDto.setAuthorsId(song.getAuthors().stream()
                    .map(Author::getId).collect(Collectors.toList()));
        }
        return shortSongDto;
    }

    @Override
    public Song mapToObjectFromShort(ShortSongDto shortSongDto) {
        Song song = new Song();
        song.setId(shortSongDto.getId());
        song.setName(shortSongDto.getName());
        song.setLyrics(shortSongDto.getLyrics());
        if (shortSongDto.getAlbumId() != null) {
            song.setAlbum(albumRepository.findById(shortSongDto.getAlbumId())
                    .orElse(null));
        }
        if (shortSongDto.getAuthorsId() != null) {
            song.setAuthors(authorRepository.findAllById(shortSongDto.getAuthorsId()));
        }
        return song;
    }

    @Override
    public List<ShortSongDto> mapToShortDtoList(List<Song> songs) {
        List<ShortSongDto> songsDto = new ArrayList<>();
        songs.forEach(song -> songsDto.add(mapToShortDto(song)));
        return songsDto;
    }

    @Override
    public List<Song> mapToObjectListFromShort(List<ShortSongDto> songsDto) {
        List<Song> songs = new ArrayList<>();
        songsDto.forEach(songDto -> songs.add(mapToObjectFromShort(songDto)));
        return songs;
    }
}
