package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.mappers.longmapper.LongSongDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.repository.SongRepository;
import com.ragnarock.musicrecommends.services.SongService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Primary
public class SongServiceImpl implements SongService {
    private final SongRepository repository;
    private final LongSongDtoMapper longSongDtoMapper;
    private final ShortSongDtoMapper shortSongDtoMapper;

    @Override
    public List<LongSongDto> findAll() {
        return longSongDtoMapper.mapToLongDtoList(repository.findAll());
    }

    @Override
    public List<LongSongDto> findByNameAndLyrics(String name, String lyrics) {
        return longSongDtoMapper.mapToLongDtoList(repository.findByNameAndLyrics(name, lyrics));
    }

    @Override
    public LongSongDto findById(Long id) {
        return longSongDtoMapper.mapToLongDto(repository.findById(id).orElse(null));
    }

    @Override
    public LongSongDto saveSong(ShortSongDto shortSongDto) {
        return longSongDtoMapper.mapToLongDto(repository
                .save(shortSongDtoMapper.mapToObjectFromShort(shortSongDto)));
    }

    @Override
    public LongSongDto updateSong(ShortSongDto shortSongDto) {
        return longSongDtoMapper.mapToLongDto(repository
                .save(shortSongDtoMapper.mapToObjectFromShort(shortSongDto)));
    }

    @Override
    @Transactional
    public void deleteSong(Long id) {
        repository.deleteById(id);
    }
}
