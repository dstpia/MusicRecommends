package com.ragnarock.musicrecommends.mappers.longmapper;

import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import java.util.List;

public interface LongSongDtoMapper {
    LongSongDto mapToLongDto(Song song);

    Song mapToObjectFromLong(LongSongDto longSongDto);

    List<LongSongDto> mapToLongDtoList(List<Song> songs);

    List<Song> mapToObjectListFromLong(List<LongSongDto> songsDto);
}
