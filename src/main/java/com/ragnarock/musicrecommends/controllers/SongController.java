package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
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
    public List<LongSongDto> findAll() {
        return songService.findAll();
    }

    @GetMapping("/find")
    public List<LongSongDto> findByNameAndLyrics(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String lyrics) {
        List<LongSongDto> songs = songService.findByNameAndLyrics(name, lyrics);
        if (songs.isEmpty()) {
            throw new UnExistedItemException("Songs not found, name: " + name
                    + ", lyrics: " + lyrics);
        }
        return songs;
    }

    @GetMapping("/{id}")
    public LongSongDto findById(@PathVariable Long id) throws UnExistedItemException {
        LongSongDto song = songService.findById(id);
        if (song == null) {
            throw new UnExistedItemException("Song not found, id: " + id);
        }
        return song;
    }

    @PostMapping("/save")
    public LongSongDto saveSong(@RequestBody ShortSongDto shortSongDto) {
        return songService.saveSong(shortSongDto);
    }

    @PutMapping("/update")
    public LongSongDto updateSong(@RequestBody ShortSongDto shortSongDto) {
        return songService.updateSong(shortSongDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSongById(@PathVariable Long id) {
        songService.deleteSong(id);
    }
}
