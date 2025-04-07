package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface SongService {

    List<LongSongDto> findAll();

    List<LongSongDto> findByNameAndLyrics(String name, String lyrics);

    LongSongDto findById(Long id);

    List<LongSongDto> findByAlbumYear(Long year);

    List<LongSongDto> findByAlbumGenre(String genre);

    LongSongDto saveSong(ShortSongDto shortSongDto);

    LongSongDto updateSong(ShortSongDto shortSongDto);

    boolean deleteSong(Long id);
}
