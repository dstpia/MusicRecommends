package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.data.Song;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemorySongDao {
    private final List<Song> songs = new ArrayList<>();

    public List<Song> findAll() {
        return songs;
    }

    public List<Song> findByNameAndLyrics(String name, String lyrics) {
        return songs.stream()
                .filter(foundedSong -> name == null || foundedSong.getName().equals(name))
                .filter(foundedSong -> lyrics == null || foundedSong.getLyrics().equals(lyrics))
                .toList();
    }

    public Song findById(Long id) {
        return songs.stream()
                .filter(foundedSong -> foundedSong.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Song saveSong(Song song) {
        songs.add(song);
        return song;
    }

    public Song updateSong(Song song) {
        int songIndex = IntStream.range(0, songs.size())
                .filter(index -> songs.get(index).getId().equals(song.getId()))
                .findFirst()
                .orElse(-1);
        if (songIndex != -1) {
            songs.set(songIndex, song);
            return song;
        }
        return null;
    }

    public void deleteSong(Song song) {
        int songIndex = IntStream.range(0, songs.size())
                .filter(index -> songs.get(index).getId().equals(song.getId()))
                .findFirst()
                .orElse(-1);
        if (songIndex > -1) {
            songs.remove(songs.get(songIndex));
        }
    }
}
