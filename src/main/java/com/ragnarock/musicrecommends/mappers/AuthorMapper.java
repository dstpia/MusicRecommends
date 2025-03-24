package com.ragnarock.musicrecommends.mappers;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.dto.AuthorDto;
import java.util.List;

public interface AuthorMapper {
    AuthorDto mapToDto(Author author);

    Author mapToObject(AuthorDto authorDto);

    List<AuthorDto> mapToDtoList(List<Author> authors);

    List<Author> mapToObjectList(List<AuthorDto> authorsDto);
}
