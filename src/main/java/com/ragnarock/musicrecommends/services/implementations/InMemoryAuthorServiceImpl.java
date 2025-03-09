package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.repository.InMemoryAuthorDao;
import com.ragnarock.musicrecommends.services.AuthorService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemoryAuthorServiceImpl implements AuthorService {
    private final InMemoryAuthorDao repository;

    @Override
    public List<Author> getAuthors(String name, String genre) {
        return repository.getAuthors(name, genre);
    }

    @Override
    public Author getAuthorById(int id) {
        return repository.getAuthorById(id);
    }

    @Override
    public Author saveAuthor(Author author) {
        return repository.saveAuthor(author);
    }

    @Override
    public Author updateAuthor(Author author) {
        return repository.updateAuthor(author);
    }

    @Override
    public void deleteAuthor(Author author) {
        repository.deleteAuthor(author);
    }
}