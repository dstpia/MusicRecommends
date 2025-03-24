package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.SongDto;
import com.ragnarock.musicrecommends.mappers.SongMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class SongMapperImpl implements SongMapper {
    private final AuthorRepository authorRepository;
    private final AlbumRepository albumRepository;

    @Override
    public SongDto mapToDto(Song song) {
        SongDto songDto = new SongDto();
        songDto.setId(song.getId());
        songDto.setName(song.getName());
        songDto.setLyrics(song.getLyrics());
        if (song.getAlbum() != null) {
            songDto.setAlbumId(song.getAlbum().getId());
        }
        if (song.getAuthors() != null) {
            songDto.setAuthorsId(song.getAuthors().stream()
                    .map(Author::getId).collect(Collectors.toList()));
        }
        return songDto;
    }

    @Override
    public Song mapToObject(SongDto songDto) {
        Song song = new Song();
        song.setId(songDto.getId());
        song.setName(songDto.getName());
        song.setLyrics(songDto.getLyrics());
        if (songDto.getAlbumId() != null) {
            song.setAlbum(albumRepository.findById(songDto.getAlbumId())
                    .orElse(null));
        }
        if (songDto.getAuthorsId() != null) {
            song.setAuthors(authorRepository.findAllById(songDto.getAuthorsId()));
        }
        return song;
    }

    @Override
    public List<SongDto> mapToDtoList(List<Song> songs) {
        List<SongDto> songsDto = new ArrayList<>();
        songs.forEach(song -> songsDto.add(mapToDto(song)));
        return songsDto;
    }

    @Override
    public List<Song> mapToObjectList(List<SongDto> songsDto) {
        List<Song> songs = new ArrayList<>();
        songsDto.forEach(songDto -> songs.add(mapToObject(songDto)));
        return songs;
    }
}
