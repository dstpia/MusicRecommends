package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.AlbumDto;
import com.ragnarock.musicrecommends.mappers.AlbumMapper;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import com.ragnarock.musicrecommends.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AlbumMapperImpl implements AlbumMapper {
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;

    @Override
    public AlbumDto mapToDto(Album album) {
        AlbumDto albumDto = new AlbumDto();
        albumDto.setId(album.getId());
        albumDto.setName(album.getName());
        albumDto.setGenre(album.getGenre());
        albumDto.setYear(album.getYear());
        if (album.getAuthor() != null) {
            albumDto.setAuthor_id(album.getAuthor().getId());
        }
        if (album.getSongs() != null) {
            albumDto.setSongs_id(album.getSongs().stream()
                    .map(Song::getId).collect(Collectors.toList()));
        }
        return albumDto;
    }

    @Override
    public Album mapToObject(AlbumDto albumDto) {
        Album album = new Album();
        album.setId(albumDto.getId());
        album.setName(albumDto.getName());
        album.setGenre(albumDto.getGenre());
        album.setYear(albumDto.getYear());
        if (albumDto.getAuthor_id() != null) {
            album.setAuthor(authorRepository.getReferenceById(albumDto.getAuthor_id()));
        }
        if (albumDto.getSongs_id() != null) {
            album.setSongs(songRepository.findAllById(albumDto.getSongs_id()));
        }
        return album;
    }

    @Override
    public List<AlbumDto> mapToDtoList(List<Album> albums) {
        List<AlbumDto> albumsDto = new ArrayList<>();
        albums.forEach(album -> albumsDto.add(mapToDto(album)));
        return albumsDto;
    }

    @Override
    public List<Album> mapToObjectList(List<AlbumDto> albumsDto) {
        List<Album> albums = new ArrayList<>();
        albumsDto.forEach(albumDto -> albums.add(mapToObject(albumDto)));
        return albums;
    }
}
