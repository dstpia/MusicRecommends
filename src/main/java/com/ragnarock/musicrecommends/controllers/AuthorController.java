package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.data.Author;
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
    public List<Author> getAuthors(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String genre) {
        List<Author> authors = authorService.getAuthors(name, genre);
        if (authors.isEmpty()) {
            throw new UnExistedItemException("Authors not found, name: " + name
                    + ", genre: " + genre);
        }
        return authors;
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) throws UnExistedItemException {
        Author author = authorService.getAuthorById(id);
        if (author == null) {
            throw new UnExistedItemException("Author not found, id: " + id);
        }
        return author;
    }

    @PostMapping("/save")
    public Author saveAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @PutMapping("/update")
    public Author updateAuthorInfo(@RequestBody Author author) {
        return authorService.updateAuthor(author);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthorById(@PathVariable int id) {
        authorService.deleteAuthor(authorService.getAuthorById(id));
    }
}
