package com.ragnarock.musicrecommends.mappers;

import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.SongDto;

import java.util.List;

public interface SongMapper {
    SongDto mapToDto(Song song);

    Song mapToObject(SongDto songDto);

    List<SongDto> mapToDtoList(List<Song> songs);

    List<Song> mapToObjectList(List<SongDto> songsDto);
}
