package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Album;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAlbumDao {
    private final List<Album> albums = new ArrayList<>();

    InMemoryAlbumDao() {
        albums.add(Album.builder().id(61)
                .name("Русское поле экспериментов")
                .year(1989)
                .genre("Панк-рок")
                .recordLabel("ГрОб Records")
                .songs(null).build());

        albums.add(Album.builder().id(612)
                .name("Innuendo")
                .year(1991)
                .genre("Рок")
                .recordLabel("Parlophone Records")
                .songs(null).build());

        albums.add(Album.builder().id(6132)
                .name("...And Justice For All")
                .year(1988)
                .genre("Метал")
                .recordLabel("Electra Records")
                .songs(null).build());

        albums.add(Album.builder().id(6231)
                .name("Subways of your mind (single)")
                .year(1984)
                .genre("Нью-вейв")
                .recordLabel("Hawkeye Tonstudio")
                .songs(null).build());
    }

    public List<Album> getAlbums(String name, String genre, String recordLabel) {
        return albums.stream()
                .filter(foundedAlbum -> name == null || foundedAlbum.getName().equals(name))
                .filter(foundedAlbum -> genre == null || foundedAlbum.getGenre().equals(genre))
                .filter(foundedAlbum -> recordLabel == null
                        || foundedAlbum.getRecordLabel().equals(recordLabel))
                .toList();
    }

    public Album getAlbumById(int id) {
        return albums.stream()
                .filter(foundedAlbum -> foundedAlbum.getId() == id)
                .findFirst().orElse(null);
    }

    public Album saveAlbum(Album album) {
        albums.add(album);
        return album;
    }

    public Album updateAlbum(Album album) {
        int albumIndex = IntStream.range(0, albums.size())
                .filter(index -> albums.get(index).getId() == album.getId())
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
                .filter(index -> albums.get(index).getId() == album.getId())
                .findFirst()
                .orElse(-1);
        if (songIndex > -1) {
            albums.remove(albums.get(songIndex));
        }
    }
}
