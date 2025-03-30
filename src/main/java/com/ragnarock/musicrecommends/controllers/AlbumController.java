package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
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
    public List<LongAlbumDto> findAll() {
        return albumService.findAll();
    }

    @GetMapping("/find")
    public List<LongAlbumDto> findByNameAndGenre(@RequestParam(required = false) String name,
                                                  @RequestParam(required = false) String genre) {
        List<LongAlbumDto> albums = albumService.findByNameAndGenre(name, genre);
        if (albums.isEmpty()) {
            throw new UnExistedItemException("Albums not found, name: " + name
                    + ", genre: " + genre);
        }
        return albums;
    }

    @GetMapping("/{id}")
    public LongAlbumDto findById(@PathVariable Long id) throws UnExistedItemException {
        LongAlbumDto album = albumService.findById(id);
        if (album == null) {
            throw new UnExistedItemException("Album not found, id: " + id);
        }
        return album;
    }

    @GetMapping("/find/{year}")
    public List<LongAlbumDto> findByYear(@PathVariable Long year) {
        return albumService.findByYear(year);
    }

    @PostMapping("/save")
    public LongAlbumDto saveAlbum(@RequestBody ShortAlbumDto shortAlbumDto) {
        return albumService.saveAlbum(shortAlbumDto);
    }

    @PutMapping("/update")
    public LongAlbumDto updateAlbumInfo(@RequestBody ShortAlbumDto shortAlbumDto) {
        return albumService.updateAlbum(shortAlbumDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAlbumById(@PathVariable Long id) {
        albumService.deleteAlbum(id);
    }
}
