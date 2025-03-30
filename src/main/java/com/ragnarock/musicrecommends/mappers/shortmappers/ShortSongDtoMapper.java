package com.ragnarock.musicrecommends.mappers.shortmappers;

import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import java.util.List;

public interface ShortSongDtoMapper {
    ShortSongDto mapToShortDto(Song song);

    Song mapToObjectFromShort(ShortSongDto shortSongDto);

    List<ShortSongDto> mapToShortDtoList(List<Song> songs);

    List<Song> mapToObjectListFromShort(List<ShortSongDto> songsDto);
}
