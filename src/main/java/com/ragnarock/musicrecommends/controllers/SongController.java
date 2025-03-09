package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.data.Song;
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
    public List<Song> getSongs(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String lyrics) {
        List<Song> songs = songService.getSongs(name, lyrics);
        if (songs.isEmpty()) {
            throw new UnExistedItemException("Songs not found, name: " + name
                    + ", lyrics: " + lyrics);
        }
        return songs;
    }

    @GetMapping("/{id}")
    public Song getSongById(@PathVariable int id) throws UnExistedItemException {
        Song song = songService.getSongById(id);
        if (song == null) {
            throw new UnExistedItemException("Song not found, id: " + id);
        }
        return song;
    }

    @PostMapping("/save")
    public Song saveSong(@RequestBody Song song) {
        return songService.saveSong(song);
    }

    @PutMapping("/update")
    public Song updateSong(@RequestBody Song song) {
        return songService.updateSong(song);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteSongById(@PathVariable int id) {
        songService.deleteSong(songService.getSongById(id));
    }
}
