package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.dto.AuthorDto;
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
    public List<AuthorDto> findAll () {
        return authorService.findAll();
    }

    @GetMapping("/find")
    public List<AuthorDto> getAuthors(@RequestParam(required = false) String name,
                                   @RequestParam(required = false) String genre) {
        List<AuthorDto> authorsDto = authorService.findByNameAndGenre(name, genre);
        if (authorsDto.isEmpty()) {
            throw new UnExistedItemException("Authors not found, name: " + name
                    + ", genre: " + genre);
        }
        return authorsDto;
    }

    @GetMapping("/{id}")
    public AuthorDto getAuthorById(@PathVariable Long id) throws UnExistedItemException {
        AuthorDto authorDto = authorService.findById(id);
        if (authorDto == null) {
            throw new UnExistedItemException("Author not found, id: " + id);
        }
        return authorDto;
    }

    @PostMapping("/save")
    public AuthorDto saveAuthor(@RequestBody AuthorDto authorDto) {
        return authorService.saveAuthor(authorDto);
    }

    @PutMapping("/update")
    public AuthorDto updateAuthorInfo(@RequestBody AuthorDto authorDto) {
        return authorService.updateAuthor(authorDto);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthorById(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}
