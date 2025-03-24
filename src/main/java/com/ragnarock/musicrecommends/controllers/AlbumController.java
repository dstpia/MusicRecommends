package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.dto.AlbumDto;
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
    public List<AlbumDto> findAll() {
        return albumService.findAll();
    }

    @GetMapping("/find")
    public List<AlbumDto> findByNameAndGenre(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String genre) {
        List<AlbumDto> albums = albumService.findByNameAndGenre(name, genre);
        if (albums.isEmpty()) {
            throw new UnExistedItemException("Albums not found, name: " + name
                    + ", genre: " + genre);
        }
        return albums;
    }

    @GetMapping("/{id}")
    public AlbumDto findById(@PathVariable Long id) throws UnExistedItemException {
        AlbumDto albumDto = albumService.findById(id);
        if (albumDto == null) {
            throw new UnExistedItemException("Album not found, id: " + id);
        }
        return albumDto;
    }

    @GetMapping("/find/{year}")
    public List<AlbumDto> findByYear(@PathVariable Long year) {
        return albumService.findByYear(year);
    }

    @PostMapping("/save")
    public AlbumDto saveAlbum(@RequestBody AlbumDto albumDto) {
        return albumService.saveAlbum(albumDto);
    }

    @PutMapping("/update")
    public AlbumDto updateAlbumInfo(@RequestBody AlbumDto albumDto) {
        return albumService.updateAlbum(albumDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAlbumById(@PathVariable Long id) {
        albumService.deleteAlbum(id);
    }
}
