package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAuthorDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAuthorDtoMapper;
import com.ragnarock.musicrecommends.repository.InMemoryAuthorDao;
import com.ragnarock.musicrecommends.services.AuthorService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemoryAuthorServiceImpl implements AuthorService {
    private final InMemoryAuthorDao repository;
    private final LongAuthorDtoMapper longAuthorDtoMapper;
    private final ShortAuthorDtoMapper shortAuthorDtoMapper;

    @Override
    public List<LongAuthorDto> findAll() {
        return longAuthorDtoMapper.mapToLongDtoList(repository.findAll());
    }

    @Override
    public List<LongAuthorDto> findByNameAndGenre(String name, String genre) {
        return longAuthorDtoMapper.mapToLongDtoList(repository.findByNameAndGenre(name, genre));
    }

    @Override
    public LongAuthorDto findById(Long id) {
        return longAuthorDtoMapper.mapToLongDto(repository.findById(id));
    }

    @Override
    public LongAuthorDto saveAuthor(ShortAuthorDto shortAuthorDto) {
        return longAuthorDtoMapper.mapToLongDto(repository
                .saveAuthor(shortAuthorDtoMapper.mapToObjectFromShort(shortAuthorDto)));
    }

    @Override
    public List<LongAuthorDto> saveAuthorsList(List<ShortAuthorDto> authors) {
        //Not available function
        return List.of();
    }

    @Override
    public LongAuthorDto updateAuthor(ShortAuthorDto shortAuthorDto) {
        return longAuthorDtoMapper.mapToLongDto(repository
                .updateAuthor(shortAuthorDtoMapper.mapToObjectFromShort(shortAuthorDto)));
    }

    @Override
    public boolean deleteAuthor(Long id) {
        repository.deleteAuthor(repository.findById(id));
        return true;
    }
}