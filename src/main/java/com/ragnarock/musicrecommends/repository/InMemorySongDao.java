package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Song;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemorySongDao {
    private final List<Song> songs = new ArrayList<>();

    InMemorySongDao() {
        songs.add(Song.builder().id(1)
                .name("Моя оборона")
                .lyrics("Пластмассовый мир победил...").build());

        songs.add(Song.builder().id(12)
                .name("Все идет по плану")
                .lyrics("Границы ключ переломлен пополам...").build());

        songs.add(Song.builder().id(132)
                .name("Семь шагов за горизонт")
                .lyrics("Покачнулось небо под ногами...").build());

        songs.add(Song.builder().id(231)
                .name("Поезд на малую землю")
                .lyrics("Отсутствует").build());
    }


    public List<Song> getSongs(String name, String lyrics) {
        return songs.stream()
                .filter(foundedSong -> name == null || foundedSong.getName().equals(name))
                .filter(foundedSong -> lyrics == null || foundedSong.getLyrics().equals(lyrics))
                .toList();
    }

    public Song getSongById(int id) {
        return songs.stream()
                .filter(foundedSong -> foundedSong.getId() == id)
                .findFirst().orElse(null);
    }

    public Song saveSong(Song song) {
        songs.add(song);
        return song;
    }

    public Song updateSong(Song song) {
        int songIndex = IntStream.range(0, songs.size())
                .filter(index -> songs.get(index).getId() == song.getId())
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
                .filter(index -> songs.get(index).getId() == song.getId())
                .findFirst()
                .orElse(-1);
        if (songIndex > -1) {
            songs.remove(songs.get(songIndex));
        }
    }
}
