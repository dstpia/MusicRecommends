package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.mappers.longmapper.LongAlbumDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAlbumDtoMapper;
import com.ragnarock.musicrecommends.repository.InMemoryAlbumDao;
import com.ragnarock.musicrecommends.services.AlbumService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemoryAlbumServiceImpl implements AlbumService {
    private final InMemoryAlbumDao repository;
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
        return longAlbumDtoMapper.mapToLongDto(repository.findById(id));
    }

    @Override
    public LongAlbumDto saveAlbum(ShortAlbumDto shortAlbumDto) {
        return longAlbumDtoMapper.mapToLongDto(repository
                .saveAlbum(shortAlbumDtoMapper.mapToObjectFromShort(shortAlbumDto)));
    }

    @Override
    public LongAlbumDto updateAlbum(ShortAlbumDto shortAlbumDto) {
        return longAlbumDtoMapper.mapToLongDto(repository
                .updateAlbum(shortAlbumDtoMapper.mapToObjectFromShort(shortAlbumDto)));
    }

    @Override
    public void deleteAlbum(Long id) {
        repository.deleteAlbum(repository.findById(id));
    }

    @Override
    public List<LongAlbumDto> findByYear(Long year) {
        return longAlbumDtoMapper.mapToLongDtoList(repository.findByYear(year));
    }
}