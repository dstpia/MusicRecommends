package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.data.Author;
import java.util.List;

import com.ragnarock.musicrecommends.dto.AuthorDto;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {

    List<AuthorDto> findAll();

    List<AuthorDto> findByNameAndGenre(String name, String genre);

    AuthorDto findById(Long id);

    AuthorDto saveAuthor(AuthorDto authorDto);

    AuthorDto updateAuthor(AuthorDto authorDto);

    void deleteAuthor(Long id);
}
