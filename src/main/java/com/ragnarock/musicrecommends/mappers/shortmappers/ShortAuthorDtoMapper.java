package com.ragnarock.musicrecommends.mappers.shortmappers;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import java.util.List;

public interface ShortAuthorDtoMapper {
    ShortAuthorDto mapToShortDto(Author author);

    Author mapToObjectFromShort(ShortAuthorDto shortAuthorDto);

    List<ShortAuthorDto> mapToShortDtoList(List<Author> authors);

    List<Author> mapToObjectListFromShort(List<ShortAuthorDto> authorsDto);
}
