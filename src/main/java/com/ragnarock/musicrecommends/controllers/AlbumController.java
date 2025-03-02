package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.exceptions.UnExistedItemException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/albums")
@AllArgsConstructor
public class AlbumController {
    //For future
    //private final AlbumService albumService;

    @GetMapping
    public List<Album> getAlbums(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) String genre,
                                 @RequestParam(required = false) String recordLabel) {
        return null;
    }

    @GetMapping("/{id}")
    public Album getAlbumById(@PathVariable int id) throws UnExistedItemException {
        return null;
    }

    @PostMapping("/save")
    public Album saveAlbum(@RequestBody Album album) {
        return null;
    }

    @PutMapping("/update")
    public Album updateAlbumInfo(@RequestBody Album album) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAlbumById(@PathVariable int id) {}
}
