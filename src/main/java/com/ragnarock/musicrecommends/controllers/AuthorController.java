package com.ragnarock.musicrecommends.controllers;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.exceptions.UnExistedItemException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/authors")
@AllArgsConstructor
public class AuthorController {
    //For future
    //private final AuthorService authorService;

    @GetMapping
    public List<Author> getAuthors(@RequestParam(required = false) String name,
                               @RequestParam(required = false) String genre) {
        return null;
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) throws UnExistedItemException {
        return null;
    }

    @PostMapping("/save")
    public Author saveAuthor(@RequestBody Author author) {
        return null;
    }

    @PutMapping("/update")
    public Author updateAuthorInfo(@RequestBody Author author) {
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAuthorById(@PathVariable int id) {}
}
