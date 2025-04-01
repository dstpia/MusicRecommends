package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAuthorDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAuthorDtoMapper;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import com.ragnarock.musicrecommends.services.AuthorService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Primary
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
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
        return longAuthorDtoMapper.mapToLongDto(repository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public LongAuthorDto saveAuthor(ShortAuthorDto shortAuthorDto) {
        return longAuthorDtoMapper.mapToLongDto(repository
                .save(shortAuthorDtoMapper.mapToObjectFromShort(shortAuthorDto)));
    }

    @Override
    @Transactional
    public LongAuthorDto updateAuthor(ShortAuthorDto shortAuthorDto) {
        return longAuthorDtoMapper.mapToLongDto(repository
                .save(shortAuthorDtoMapper.mapToObjectFromShort(shortAuthorDto)));
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        repository.deleteById(id);
    }
}
