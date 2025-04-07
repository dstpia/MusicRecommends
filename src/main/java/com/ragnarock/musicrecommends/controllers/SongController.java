package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.exceptions.UnExistedItemException;
import com.ragnarock.musicrecommends.services.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/songs")
@AllArgsConstructor
@Tag(name = "Song controller", description = "API для работы с песнями")
public class SongController {
    private final SongService songService;

    @GetMapping
    @Operation(summary = "Получить все песни",
            description = "Возвращает все песни что есть в репозитории или кэше")
    @ApiResponse(responseCode = "200", description = "Песни найдены")
    public ResponseEntity<List<LongSongDto>> findAll() {
        List<LongSongDto> songs = songService.findAll();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/find")
    @Operation(summary = "Поиск песен по имени и тексту",
            description = "Возвращает список песен по имени и/или тексту")
    @ApiResponse(responseCode = "200", description = "Песни найдены")
    @ApiResponse(responseCode = "404", description = "Песни не найдены")
    public ResponseEntity<List<LongSongDto>> findByNameAndLyrics(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lyrics)
            throws UnExistedItemException {
        List<LongSongDto> songs = songService.findByNameAndLyrics(name, lyrics);
        if (songs.isEmpty()) {
            throw new UnExistedItemException("Songs not found, name: " + name
                    + ", lyrics: " + lyrics);
        }
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить песню по ID",
            description = "Возвращает песню по её уникальному идентификатору")
    @ApiResponse(responseCode = "200", description = "Песня найдена")
    @ApiResponse(responseCode = "404", description = "Песня не найдена")
    public ResponseEntity<LongSongDto> findById(@PathVariable Long id)
            throws UnExistedItemException {
        LongSongDto song = songService.findById(id);
        if (song.getId() == null) {
            throw new UnExistedItemException("Song not found, id: " + id);
        }
        return ResponseEntity.ok(song);
    }

    @GetMapping("/find/albumYear/{year}")
    @Operation(summary = "Поиск песен по году альбома",
            description = "Возвращает список песен по году альбома")
    @ApiResponse(responseCode = "200", description = "Песни найдены")
    @ApiResponse(responseCode = "404", description = "Песни не найдены")
    public ResponseEntity<List<LongSongDto>> findByAlbumYear(@PathVariable Long year)
            throws UnExistedItemException {
        List<LongSongDto> songs = songService.findByAlbumYear(year);
        if (songs.isEmpty()) {
            throw new UnExistedItemException("Songs not found, year: " + year);
        }
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/find/albumGenre")
    @Operation(summary = "Поиск песен по жанру альбома",
            description = "Возвращает список песен по жанру альбома")
    @ApiResponse(responseCode = "200", description = "Песни найдены")
    @ApiResponse(responseCode = "404", description = "Песни не найдены")
    public ResponseEntity<List<LongSongDto>> findByAlbumGenre(
            @RequestParam(required = false) String genre)
            throws UnExistedItemException {
        List<LongSongDto> songs = songService.findByAlbumGenre(genre);
        if (songs.isEmpty()) {
            throw new UnExistedItemException("Songs not found, genre: " + genre);
        }
        return ResponseEntity.ok(songs);
    }

    @PostMapping("/save")
    @Operation(summary = "Сохранить новую песню",
            description = "Сохраняет новую песню в репозитории")
    @ApiResponse(responseCode = "200", description = "Песня сохранена")
    public ResponseEntity<LongSongDto> saveSong(@RequestBody ShortSongDto shortSongDto) {
        LongSongDto song = songService.saveSong(shortSongDto);
        return ResponseEntity.ok(song);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить информацию о песне",
            description = "Обновляет информацию о существующей песне")
    @ApiResponse(responseCode = "200", description = "Песня обновлена")
    @ApiResponse(responseCode = "404", description = "Песня не найдена")
    public ResponseEntity<LongSongDto> updateSong(@RequestBody ShortSongDto shortSongDto)
            throws UnExistedItemException {
        LongSongDto song = songService.updateSong(shortSongDto);
        if (song == null) {
            log.error("Song not found, impossible to update, id: {}", shortSongDto.getId());
            throw new UnExistedItemException("Song not found, impossible to update, id: "
                    + shortSongDto.getId());
        }
        return ResponseEntity.ok(song);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить песню по ID",
            description = "Удаляет песню по её уникальному идентификатору")
    @ApiResponse(responseCode = "200", description = "Песня удалена")
    @ApiResponse(responseCode = "404", description = "Песня не найдена")
    public boolean deleteSongById(@PathVariable Long id)
            throws UnExistedItemException {
        if (songService.deleteSong(id)) {
            log.info("Deleted song with id: {}", id);
            return true;
        } else {
            log.error("Can't delete song with id: {}", id);
            throw new UnExistedItemException("Can't delete song with id: " + id);
        }
    }
}
