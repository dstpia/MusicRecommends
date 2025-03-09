package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.data.Author;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {

    List<Author> getAuthors(String name, String genre);

    Author getAuthorById(int id);

    Author saveAuthor(Author author);

    Author updateAuthor(Author author);

    void deleteAuthor(Author author);
}
