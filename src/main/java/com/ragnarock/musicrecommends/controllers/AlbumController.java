package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.exceptions.UnExistedItemException;
import com.ragnarock.musicrecommends.services.AlbumService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/albums")
@AllArgsConstructor
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping
    public List<Album> getAlbums(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String genre,
                                 @RequestParam(required = false) String recordLabel) {
        List<Album> albums = albumService.getAlbums(name, genre, recordLabel);
        if (albums.isEmpty()) {
            throw new UnExistedItemException("Albums not found, name: " + name
                    + ", genre: " + genre + ", recordLabel: " + recordLabel);
        }
        return albums;
    }

    @GetMapping("/{id}")
    public Album getAlbumById(@PathVariable int id) throws UnExistedItemException {
        Album album = albumService.getAlbumById(id);
        if (album == null) {
            throw new UnExistedItemException("Album not found, id: " + id);
        }
        return album;
    }

    @PostMapping("/save")
    public Album saveAlbum(@RequestBody Album album) {
        return albumService.saveAlbum(album);
    }

    @PutMapping("/update")
    public Album updateAlbumInfo(@RequestBody Album album) {
        return albumService.updateAlbum(album);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAlbumById(@PathVariable int id) {
        albumService.deleteAlbum(albumService.getAlbumById(id));
    }
}
