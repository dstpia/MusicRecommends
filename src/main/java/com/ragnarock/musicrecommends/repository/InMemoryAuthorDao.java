package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Author;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryAuthorDao {
    private final List<Author> authors = new ArrayList<>();

    public List<Author> findAll() {
        return authors;
    }

    public List<Author> findByNameAndGenre(String name, String genre) {
        return authors.stream()
                .filter(foundedAuthor -> name == null || foundedAuthor.getName().equals(name))
                .filter(foundedAuthor -> genre == null || foundedAuthor.getGenre().equals(genre))
                .toList();
    }

    public Author findById(Long id) {
        return authors.stream()
                .filter(foundedAuthor -> foundedAuthor.getId().equals(id))
                .findFirst().orElse(null);
    }

    public Author saveAuthor(Author author) {
        authors.add(author);
        return author;
    }

    public Author updateAuthor(Author author) {
        int authorIndex = IntStream.range(0, authors.size())
                .filter(index -> authors.get(index).getId().equals(author.getId()))
                .findFirst()
                .orElse(-1);
        if (authorIndex != -1) {
            authors.set(authorIndex, author);
            return author;
        }
        return null;
    }

    public void deleteAuthor(Author song) {
        int songIndex = IntStream.range(0, authors.size())
                .filter(index -> authors.get(index).getId().equals(song.getId()))
                .findFirst()
                .orElse(-1);
        if (songIndex > -1) {
            authors.remove(authors.get(songIndex));
        }
    }
}
