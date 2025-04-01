package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAlbumDtoMapper;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import com.ragnarock.musicrecommends.repository.SongRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShortAlbumDtoMapperImpl implements ShortAlbumDtoMapper {
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;

    @Override
    public ShortAlbumDto mapToShortDto(Album album) {
        ShortAlbumDto shortAlbumDto = new ShortAlbumDto();
        if (album != null) {
            shortAlbumDto.setId(album.getId());
            shortAlbumDto.setName(album.getName());
            shortAlbumDto.setGenre(album.getGenre());
            shortAlbumDto.setYear(album.getYear());
            if (album.getAuthor() != null) {
                shortAlbumDto.setAuthorId(album.getAuthor().getId());
            }
            if (album.getSongs() != null) {
                shortAlbumDto.setSongsId(album.getSongs().stream()
                        .map(Song::getId).collect(Collectors.toList()));
            }
        }
        return shortAlbumDto;
    }

    @Override
    public Album mapToObjectFromShort(ShortAlbumDto shortAlbumDto) {
        Album album = new Album();
        if (shortAlbumDto != null) {
            album.setId(shortAlbumDto.getId());
            album.setName(shortAlbumDto.getName());
            album.setGenre(shortAlbumDto.getGenre());
            album.setYear(shortAlbumDto.getYear());
            if (shortAlbumDto.getAuthorId() != null) {
                album.setAuthor(authorRepository.getReferenceById(shortAlbumDto.getAuthorId()));
            }
            if (shortAlbumDto.getSongsId() != null) {
                album.setSongs(songRepository.findAllById(shortAlbumDto.getSongsId()));
            }
        }
        return album;
    }

    @Override
    public List<ShortAlbumDto> mapToShortDtoList(List<Album> albums) {
        List<ShortAlbumDto> albumsDto = new ArrayList<>();
        if (albums != null) {
            albums.forEach(album -> albumsDto.add(mapToShortDto(album)));
        }
        return albumsDto;
    }

    @Override
    public List<Album> mapToObjectListFromShort(List<ShortAlbumDto> albumsDto) {
        List<Album> albums = new ArrayList<>();
        if (albumsDto != null) {
            albumsDto.forEach(albumDto -> albums.add(mapToObjectFromShort(albumDto)));
        }
        return albums;
    }
}
