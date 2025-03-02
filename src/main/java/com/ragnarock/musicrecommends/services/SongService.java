package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.data.Song;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface SongService {

    List<Song> getSongs(String name, String lyrics);

    Song getSongById(int id);

    Song saveSong(Song song);

    Song updateSong(Song song);

    void deleteSong(Song song);
}
