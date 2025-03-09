package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.repository.InMemoryAlbumDao;
import com.ragnarock.musicrecommends.services.AlbumService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemoryAlbumServiceImpl implements AlbumService {
    private final InMemoryAlbumDao repository;

    @Override
    public List<Album> getAlbums(String name, String genre, String recordLabel) {
        return repository.getAlbums(name, genre, recordLabel);
    }

    @Override
    public Album getAlbumById(int id) {
        return repository.getAlbumById(id);
    }

    @Override
    public Album saveAlbum(Album album) {
        return repository.saveAlbum(album);
    }

    @Override
    public Album updateAlbum(Album album) {
        return repository.updateAlbum(album);
    }

    @Override
    public void deleteAlbum(Album album) {
        repository.deleteAlbum(album);
    }
}