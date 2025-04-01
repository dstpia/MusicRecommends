package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAlbumDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAlbumDtoMapper;
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
    private final LongAlbumDtoMapper longAlbumDtoMapper;
    private final ShortAlbumDtoMapper shortAlbumDtoMapper;

    @Override
    public List<LongAlbumDto> findAll() {
        return longAlbumDtoMapper.mapToLongDtoList(repository.findAll());
    }

    @Override
    public List<LongAlbumDto> findByNameAndGenre(String name, String genre) {
        return longAlbumDtoMapper.mapToLongDtoList(repository.findByNameAndGenre(name, genre));
    }

    @Override
    public LongAlbumDto findById(Long id) {
        return longAlbumDtoMapper.mapToLongDto(repository.findById(id).orElse(null));
    }

    @Override
    @Transactional
    public LongAlbumDto saveAlbum(ShortAlbumDto shortAlbumDto) {
        return longAlbumDtoMapper.mapToLongDto(repository
                .save(shortAlbumDtoMapper.mapToObjectFromShort(shortAlbumDto)));
    }

    @Override
    @Transactional
    public LongAlbumDto updateAlbum(ShortAlbumDto shortAlbumDto) {
        return longAlbumDtoMapper.mapToLongDto(repository
                .save(shortAlbumDtoMapper.mapToObjectFromShort(shortAlbumDto)));
    }

    @Override
    @Transactional
    public void deleteAlbum(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<LongAlbumDto> findByYear(Long year) {
        return longAlbumDtoMapper.mapToLongDtoList(repository.findByYear(year));
    }
}
