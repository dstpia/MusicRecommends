package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.data.Album;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AlbumService {

    List<Album> getAlbums(String name, String genre, String recordLabel);

    Album getAlbumById(int id);

    Album saveAlbum(Album album);

    Album updateAlbum(Album album);

    void deleteAlbum(Album album);
}
