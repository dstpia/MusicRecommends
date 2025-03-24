package com.ragnarock.musicrecommends.mappers;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.dto.AlbumDto;

import java.util.List;

public interface AlbumMapper {
    AlbumDto mapToDto(Album album);

    Album mapToObject(AlbumDto albumDto);

    List<AlbumDto> mapToDtoList(List<Album> albums);

    List<Album> mapToObjectList(List<AlbumDto> albumsDto);
}
