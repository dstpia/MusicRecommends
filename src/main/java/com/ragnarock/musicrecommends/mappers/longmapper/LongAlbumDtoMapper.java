package com.ragnarock.musicrecommends.mappers.longmapper;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import java.util.List;

public interface LongAlbumDtoMapper {
    LongAlbumDto mapToLongDto(Album album);

    Album mapToObjectFromLong(LongAlbumDto longAlbumDto);

    List<LongAlbumDto> mapToLongDtoList(List<Album> albums);

    List<Album> mapToObjectListFromLong(List<LongAlbumDto> albumsDto);
}
