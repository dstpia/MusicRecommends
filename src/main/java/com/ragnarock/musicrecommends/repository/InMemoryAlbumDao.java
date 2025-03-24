package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Album;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAlbumDao {
    private final List<Album> albums = new ArrayList<>();

    public List<Album> findAll() {
        return albums;
    }

    public List<Album> findByNameAndGenre(String name, String genre) {
        return albums.stream()
                .filter(foundedAlbum -> name == null || foundedAlbum.getName().equals(name))
                .filter(foundedAlbum -> genre == null || foundedAlbum.getGenre().equals(genre))
                .toList();
    }

    public Album findById(Long id) {
        return albums.stream()
                .filter(foundedAlbum -> foundedAlbum.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Album saveAlbum(Album album) {
        albums.add(album);
        return album;
    }

    public Album updateAlbum(Album album) {
        int albumIndex = IntStream.range(0, albums.size())
                .filter(index -> albums.get(index).getId().equals(album.getId()))
                .findFirst()
                .orElse(-1);
        if (albumIndex != -1) {
            albums.set(albumIndex, album);
            return album;
        }
        return null;
    }

    public void deleteAlbum(Album album) {
        int songIndex = IntStream.range(0, albums.size())
                .filter(index -> albums.get(index).getId().equals(album.getId()))
                .findFirst()
                .orElse(-1);
        if (songIndex > -1) {
            albums.remove(albums.get(songIndex));
        }
    }

    public List<Album> findByYear(Long year) {
        return albums.stream()
                .filter(foundedAlbum -> Objects.requireNonNull(foundedAlbum.getYear()).equals(year))
                .toList();
    }
}
