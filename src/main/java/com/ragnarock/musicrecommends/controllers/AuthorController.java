package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.exceptions.UnExistedItemException;
import com.ragnarock.musicrecommends.services.AuthorService;
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
@RequestMapping("/api/authors")
@AllArgsConstructor
@Tag(name = "Author controller", description = "API для работы с авторами")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    @Operation(summary = "Получить всех авторов",
            description = "Возвращает всех авторов из репозитория или кэша")
    @ApiResponse(responseCode = "200", description = "Авторы найдены")
    public ResponseEntity<List<LongAuthorDto>> findAll() {
        List<LongAuthorDto> authors = authorService.findAll();
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/find")
    @Operation(summary = "Поиск авторов по имени и жанру",
            description = "Возвращает список авторов по имени и/или жанру")
    @ApiResponse(responseCode = "200", description = "Авторы найдены")
    @ApiResponse(responseCode = "404", description = "Авторы не найдены")
    public ResponseEntity<List<LongAuthorDto>> getAuthors(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String genre)
            throws UnExistedItemException {
        List<LongAuthorDto> authors = authorService.findByNameAndGenre(name, genre);
        log.info("Found {} authors", authors.size());
        if (authors.isEmpty()) {
            log.error("Authors not found, name: {}, genre: {}", name, genre);
            throw new UnExistedItemException("Authors not found, name: " + name
                    + ", genre: " + genre);
        }
        return ResponseEntity.ok(authors);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить автора по ID",
            description = "Возвращает автора по его уникальному идентификатору")
    @ApiResponse(responseCode = "200", description = "Автор найден")
    @ApiResponse(responseCode = "404", description = "Автор не найден")
    public ResponseEntity<LongAuthorDto> getAuthorById(@PathVariable Long id)
            throws UnExistedItemException {
        LongAuthorDto author = authorService.findById(id);
        if (author.getId() == null) {
            log.error("Author not found, id: {}", id);
            throw new UnExistedItemException("Author not found, id: " + id);
        }
        log.info("Found author, id: {}", author.getId());
        return ResponseEntity.ok(author);
    }

    @PostMapping("/save")
    @Operation(summary = "Сохранить нового автора",
            description = "Сохраняет нового автора в репозитории")
    @ApiResponse(responseCode = "200", description = "Автор сохранён")
    public ResponseEntity<LongAuthorDto> saveAuthor(@RequestBody ShortAuthorDto shortAuthorDto) {
        LongAuthorDto author = authorService.saveAuthor(shortAuthorDto);
        return ResponseEntity.ok(author);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить информацию об авторе",
            description = "Обновляет информацию о существующем авторе")
    @ApiResponse(responseCode = "200", description = "Автор обновлён")
    @ApiResponse(responseCode = "404", description = "Автор не найден")
    public ResponseEntity<LongAuthorDto> updateAuthorInfo(
            @RequestBody ShortAuthorDto shortAuthorDto)
            throws UnExistedItemException {
        LongAuthorDto author = authorService.updateAuthor(shortAuthorDto);
        if (author == null) {
            log.error("Author not found, impossible to update, id: {}", shortAuthorDto.getId());
            throw new UnExistedItemException("Author not found, impossible to update, id:"
                    + shortAuthorDto.getId());
        }
        return ResponseEntity.ok(author);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удалить автора по ID",
            description = "Удаляет автора по его уникальному идентификатору")
    @ApiResponse(responseCode = "200", description = "Автор удалён")
    @ApiResponse(responseCode = "404", description = "Автор не найден")
    public boolean deleteAuthorById(@PathVariable Long id)
            throws UnExistedItemException {
        if (authorService.deleteAuthor(id)) {
            log.info("Deleted author with id: {}", id);
            return true;
        } else {
            log.error("Can't delete author with id: {}", id);
            throw new UnExistedItemException("Can't delete author with id: " + id);
        }
    }
}
