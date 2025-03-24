package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.AuthorDto;
import com.ragnarock.musicrecommends.mappers.AuthorMapper;
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
        return mapper.mapToDto(repository.findById(id).orElse(null));
    }

    @Override
    public AuthorDto saveAuthor(AuthorDto authorDto) {
        return mapper.mapToDto(repository.save(mapper.mapToObject(authorDto)));
    }

    @Override
    public AuthorDto updateAuthor(AuthorDto authorDto) {
        return mapper.mapToDto(repository.save(mapper.mapToObject(authorDto)));
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        repository.deleteById(id);
    }
}
