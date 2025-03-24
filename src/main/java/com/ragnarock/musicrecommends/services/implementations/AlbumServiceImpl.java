package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.dto.AlbumDto;
import com.ragnarock.musicrecommends.mappers.AlbumMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.services.AlbumService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Primary
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository repository;
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
        return mapper.mapToDto(repository.findById(id).orElse(null));
    }

    @Override
    public AlbumDto saveAlbum(AlbumDto albumDto) {
        return mapper.mapToDto(repository.save(mapper.mapToObject(albumDto)));
    }

    @Override
    public AlbumDto updateAlbum(AlbumDto albumDto) {
        return mapper.mapToDto(repository.save(mapper.mapToObject(albumDto)));
    }

    @Override
    @Transactional
    public void deleteAlbum(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<AlbumDto> findByYear(Long year) {
        return mapper.mapToDtoList(repository.findByYear(year));
    }
}
