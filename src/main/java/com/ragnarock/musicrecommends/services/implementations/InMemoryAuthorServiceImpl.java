package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.AuthorDto;
import com.ragnarock.musicrecommends.mappers.AuthorMapper;
import com.ragnarock.musicrecommends.repository.InMemoryAuthorDao;
import com.ragnarock.musicrecommends.services.AuthorService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemoryAuthorServiceImpl implements AuthorService {
    private final InMemoryAuthorDao repository;
    private final AuthorMapper mapper;

    @Override
    public List<AuthorDto> findAll() {
        return mapper.mapToDtoList(repository.findAll());
    }

    @Override
    public List<AuthorDto> findByNameAndGenre(String name, String genre) {
        return mapper.mapToDtoList(repository.findByNameAndGenre(name, genre));
    }

    @Override
    public AuthorDto findById(Long id) {
        return mapper.mapToDto(repository.findById(id));
    }

    @Override
    public AuthorDto saveAuthor(AuthorDto authorDto) {
        return mapper.mapToDto(repository.saveAuthor(mapper.mapToObject(authorDto)));
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        return mapper.mapToDto(repository.updateAuthor(mapper.mapToObject(authorDto)));
    }

    @Override
    public void deleteAuthor(Long id) {
        repository.deleteAuthor(repository.findById(id));
    }
}