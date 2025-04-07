package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AuthorService {

    List<LongAuthorDto> findAll();

    List<LongAuthorDto> findByNameAndGenre(String name, String genre);

    LongAuthorDto findById(Long id);

    LongAuthorDto saveAuthor(ShortAuthorDto shortAuthorDto);

    LongAuthorDto updateAuthor(ShortAuthorDto shortAuthorDto);

    boolean deleteAuthor(Long id);
}
