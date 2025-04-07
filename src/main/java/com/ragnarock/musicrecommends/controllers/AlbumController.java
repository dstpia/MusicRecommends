package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.exceptions.UnExistedItemException;
import com.ragnarock.musicrecommends.services.AlbumService;
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
@RequestMapping("/api/albums")
@AllArgsConstructor
@Tag(name = "Album controller", description = "API для работы с альбомами")
public class AlbumController {
    private final AlbumService albumService;

    @GetMapping
    @Operation(summary = "Получить все альбомы",
            description = "Возвращает все альбомы что есть в репозитории или кэше")
    @ApiResponse(responseCode = "200", description = "Альбомы найдены")
    public ResponseEntity<List<LongAlbumDto>> findAll() {
        List<LongAlbumDto> albums = albumService.findAll();
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/find")
    @Operation(summary = "Поиск альбомов по имени и жанру",
            description = "Возвращает список альбомов по имени и/или жанру")
    @ApiResponse(responseCode = "200", description = "Альбомы найдены")
    @ApiResponse(responseCode = "404", description = "Альбомы не найдены")
    public ResponseEntity<List<LongAlbumDto>> findByNameAndGenre(
            @RequestParam(required = false) String name,
             @RequestParam(required = false) String genre)
            throws UnExistedItemException {
        List<LongAlbumDto> albums = albumService.findByNameAndGenre(name, genre);
        if (albums.isEmpty()) {
            throw new UnExistedItemException("Albums not found, name: " + name
                    + ", genre: " + genre);
        }
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить альбом по ID",
            description = "Возвращает альбом по его уникальному идентификатору")
    @ApiResponse(responseCode = "200", description = "Альбом найден")
    @ApiResponse(responseCode = "404", description = "Альбом не найден")
    public ResponseEntity<LongAlbumDto> findById(@PathVariable Long id)
            throws UnExistedItemException {
        LongAlbumDto album = albumService.findById(id);
        if (album.getId() == null) {
            throw new UnExistedItemException("Album not found, id: " + id);
        }
        return ResponseEntity.ok(album);
    }

    @GetMapping("/find/{year}")
    @Operation(summary = "Поиск альбомов по году",
            description = "Возвращает список альбомов по году")
    @ApiResponse(responseCode = "200", description = "Альбомы найдены")
    @ApiResponse(responseCode = "404", description = "Альбомы не найдены")
    public ResponseEntity<List<LongAlbumDto>> findByYear(@PathVariable Long year)
            throws UnExistedItemException {
        List<LongAlbumDto> albums = albumService.findByYear(year);
        if (albums.isEmpty()) {
            throw new UnExistedItemException("Albums not found, year: " + year);
        }
        return ResponseEntity.ok(albums);
    }

    @PostMapping("/save")
    @Operation(summary = "Сохранить новый альбом",
            description = "Сохраняет новый альбом в репозитории")
    @ApiResponse(responseCode = "200", description = "Альбом сохранён")
    public ResponseEntity<LongAlbumDto> saveAlbum(@RequestBody ShortAlbumDto shortAlbumDto) {
        LongAlbumDto album = albumService.saveAlbum(shortAlbumDto);
        return ResponseEntity.ok(album);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить информацию об альбоме",
            description = "Обновляет информацию о существующем альбоме")
    @ApiResponse(responseCode = "200", description = "Альбом обновлён")
    @ApiResponse(responseCode = "404", description = "Альбом не найден")
    public ResponseEntity<LongAlbumDto> updateAlbumInfo(@RequestBody ShortAlbumDto shortAlbumDto)
            throws UnExistedItemException {
        LongAlbumDto album = albumService.updateAlbum(shortAlbumDto);
        if (album == null) {
            log.error("Album not found, impossible to update, id: {}", shortAlbumDto.getId());
            throw new UnExistedItemException("Album not found, impossible to update, id: "
                    + shortAlbumDto.getId());
        }
        return ResponseEntity.ok(album);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить альбом по ID",
            description = "Удаляет альбом по его уникальному идентификатору")
    @ApiResponse(responseCode = "200", description = "Альбом удалён")
    @ApiResponse(responseCode = "404", description = "Альбом не найден")
    public boolean deleteAlbumById(@PathVariable Long id)
            throws UnExistedItemException {
        if (albumService.deleteAlbum(id)) {
            log.info("Deleted album with id: {}", id);
            return true;
        } else {
            log.error("Can't delete album with id: {}", id);
            throw new UnExistedItemException("Can't delete album with id: " + id);
        }
    }
}
