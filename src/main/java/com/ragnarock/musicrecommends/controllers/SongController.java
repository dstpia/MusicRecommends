package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.dto.SongDto;
import com.ragnarock.musicrecommends.exceptions.UnExistedItemException;
import com.ragnarock.musicrecommends.services.SongService;
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
@RequestMapping("/api/songs")
@AllArgsConstructor
public class SongController {
    private final SongService songService;

    @GetMapping
    public List<SongDto> findAll() {
        return songService.findAll();
    }

    @GetMapping("/find")
    public List<SongDto> findByNameAndLyrics(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String lyrics) {
        List<SongDto> songsDto = songService.findByNameAndLyrics(name, lyrics);
        if (songsDto.isEmpty()) {
            throw new UnExistedItemException("Songs not found, name: " + name
                    + ", lyrics: " + lyrics);
        }
        return songsDto;
    }

    @GetMapping("/{id}")
    public SongDto findById(@PathVariable Long id) throws UnExistedItemException {
        SongDto songDto = songService.findById(id);
        if (songDto == null) {
            throw new UnExistedItemException("Song not found, id: " + id);
        }
        return songDto;
    }

    @PostMapping("/save")
    public SongDto saveSong(@RequestBody SongDto songDto) {
        return songService.saveSong(songDto);
    }

    @PutMapping("/update")
    public SongDto updateSong(@RequestBody SongDto songDto) {
        return songService.updateSong(songDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSongById(@PathVariable Long id) {
        songService.deleteSong(id);
    }
}
