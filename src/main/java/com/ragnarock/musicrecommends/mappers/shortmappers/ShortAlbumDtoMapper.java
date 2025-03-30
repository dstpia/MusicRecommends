package com.ragnarock.musicrecommends.mappers.shortmappers;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import java.util.List;

public interface ShortAlbumDtoMapper {
    ShortAlbumDto mapToShortDto(Album album);

    Album mapToObjectFromShort(ShortAlbumDto shortAlbumDto);

    List<ShortAlbumDto> mapToShortDtoList(List<Album> albums);

    List<Album> mapToObjectListFromShort(List<ShortAlbumDto> albumsDto);
}

