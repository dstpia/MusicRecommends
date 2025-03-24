package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.AlbumDto;
import com.ragnarock.musicrecommends.mappers.AlbumMapper;
import com.ragnarock.musicrecommends.repository.InMemoryAlbumDao;
import com.ragnarock.musicrecommends.services.AlbumService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemoryAlbumServiceImpl implements AlbumService {
    private final InMemoryAlbumDao repository;
    private final AlbumMapper mapper;

    @Override
    public List<AlbumDto> findAll() {
        return mapper.mapToDtoList(repository.findAll());
    }

    @Override
    public List<AlbumDto> findByNameAndGenre(String name, String genre) {
        return mapper.mapToDtoList(repository.findByNameAndGenre(name, genre));
    }

    @Override
    public AlbumDto findById(Long id) {
        return mapper.mapToDto(repository.findById(id));
    }

    @Override
    public AlbumDto saveAlbum(AlbumDto albumDto) {
        return mapper.mapToDto(repository.saveAlbum(mapper.mapToObject(albumDto)));
    }

    @Override
    public AlbumDto updateAlbum(AlbumDto albumDto) {
        return mapper.mapToDto(repository.updateAlbum(mapper.mapToObject(albumDto)));
    }

    @Override
    public void deleteAlbum(Long id) {
        repository.deleteAlbum(repository.findById(id));
    }

    @Override
    public List<AlbumDto> findByYear(Long year) {
        return mapper.mapToDtoList(repository.findByYear(year));
    }
}