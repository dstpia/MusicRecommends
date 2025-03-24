package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.data.Song;
import java.util.List;

import com.ragnarock.musicrecommends.dto.SongDto;
import org.springframework.stereotype.Service;

@Service
public interface SongService {

    List<SongDto> findAll();

    List<SongDto> findByNameAndLyrics(String name, String lyrics);

    SongDto findById(Long id);

    SongDto saveSong(SongDto songDto);

    SongDto updateSong(SongDto songDto);

    void deleteSong(Long id);
}
