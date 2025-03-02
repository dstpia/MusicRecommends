package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.repository.InMemorySongDao;
import com.ragnarock.musicrecommends.services.SongService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemorySongServiceImpl implements SongService {
    private final InMemorySongDao repository;

    @Override
    public List<Song> getSongs(String name, String lyrics) {
        return repository.getSongs(name, lyrics);
    }

    @Override
    public Song getSongById(int id) {
        return repository.getSongById(id);
    }

    @Override
    public Song saveSong(Song song) {
        return repository.saveSong(song);
    }

    @Override
    public Song updateSong(Song song) {
        return repository.updateSong(song);
    }

    @Override
    public void deleteSong(Song song) {
        repository.deleteSong(song);
    }
}