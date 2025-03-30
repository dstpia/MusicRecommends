package com.ragnarock.musicrecommends.mappers.longmappers;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import java.util.List;

public interface LongAuthorDtoMapper {
    LongAuthorDto mapToLongDto(Author author);

    Author mapToObjectFromLong(LongAuthorDto longAuthorDto);

    List<LongAuthorDto> mapToLongDtoList(List<Author> authors);

    List<Author> mapToObjectListFromLong(List<LongAuthorDto> authorsDto);
}
