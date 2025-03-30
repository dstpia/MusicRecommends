package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.exceptions.UnExistedItemException;
import com.ragnarock.musicrecommends.services.AuthorService;
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
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public List<LongAuthorDto> findAll() {
        return authorService.findAll();
    }

    @GetMapping("/find")
    public List<LongAuthorDto> getAuthors(@RequestParam(required = false) String name,
                                           @RequestParam(required = false) String genre) {
        List<LongAuthorDto> authors = authorService.findByNameAndGenre(name, genre);
        if (authors.isEmpty()) {
            throw new UnExistedItemException("Authors not found, name: " + name
                    + ", genre: " + genre);
        }
        return authors;
    }

    @GetMapping("/{id}")
    public LongAuthorDto getAuthorById(@PathVariable Long id) throws UnExistedItemException {
        LongAuthorDto author = authorService.findById(id);
        if (author == null) {
            throw new UnExistedItemException("Author not found, id: " + id);
        }
        return author;
    }

    @PostMapping("/save")
    public LongAuthorDto saveAuthor(@RequestBody ShortAuthorDto shortAuthorDto) {
        return authorService.saveAuthor(shortAuthorDto);
    }

    @PutMapping("/update")
    public LongAuthorDto updateAuthorInfo(@RequestBody ShortAuthorDto shortAuthorDto) {
        return authorService.updateAuthor(shortAuthorDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
