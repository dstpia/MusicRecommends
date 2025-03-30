package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAlbumDtoMapper;
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
public class LongAlbumDtoMapperImpl implements LongAlbumDtoMapper {
    private final AlbumRepository albumRepository;
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;
    private final ShortAuthorDtoMapper shortAuthorDtoMapper;
    private final ShortSongDtoMapper shortSongDtoMapper;
    private final ShortAlbumDtoMapper shortAlbumDtoMapper;

    @Override
    public LongAlbumDto mapToLongDto(Album album) {
        LongAlbumDto longAlbumDto = new LongAlbumDto();
        longAlbumDto.setId(album.getId());
        longAlbumDto.setName(album.getName());
        longAlbumDto.setGenre(album.getGenre());
        longAlbumDto.setYear(album.getYear());
        longAlbumDto.setAuthor(shortAuthorDtoMapper.mapToShortDto(album.getAuthor()));
        longAlbumDto.setSongs(shortSongDtoMapper.mapToShortDtoList(album.getSongs()));
        return longAlbumDto;
    }

    @Override
    public Album mapToObjectFromLong(LongAlbumDto longAlbumDto) {
        Album album = new Album();
        album.setId(longAlbumDto.getId());
        album.setName(longAlbumDto.getName());
        album.setGenre(longAlbumDto.getGenre());
        album.setYear(longAlbumDto.getYear());
        ShortAlbumDto shortAlbumDto = shortAlbumDtoMapper.mapToShortDto(albumRepository
                .getReferenceById(longAlbumDto.getId()));
        if (shortAlbumDto.getAuthorId() != null) {
            album.setAuthor(authorRepository.getReferenceById(shortAlbumDto.getAuthorId()));
        }
        if (shortAlbumDto.getSongsId() != null) {
            album.setSongs(songRepository.findAllById(shortAlbumDto.getSongsId()));
        }
        return album;
    }

    @Override
    public List<LongAlbumDto> mapToLongDtoList(List<Album> albums) {
        List<LongAlbumDto> albumsDto = new ArrayList<>();
        albums.forEach(album -> albumsDto.add(mapToLongDto(album)));
        return albumsDto;
    }

    @Override
    public List<Album> mapToObjectListFromLong(List<LongAlbumDto> albumsDto) {
        List<Album> albums = new ArrayList<>();
        albumsDto.forEach(albumDto -> albums.add(mapToObjectFromLong(albumDto)));
        return albums;
    }
}
