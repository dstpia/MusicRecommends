package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongSongDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.repository.InMemorySongDao;
import com.ragnarock.musicrecommends.services.SongService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemorySongServiceImpl implements SongService {
    private final InMemorySongDao repository;
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
        return longSongDtoMapper.mapToLongDto(repository.findById(id));
    }

    @Override
    public LongSongDto saveSong(ShortSongDto shortSongDto) {
        return longSongDtoMapper.mapToLongDto(repository
                .saveSong(shortSongDtoMapper.mapToObjectFromShort(shortSongDto)));
    }

    @Override
    public LongSongDto updateSong(ShortSongDto shortSongDto) {
        return longSongDtoMapper.mapToLongDto(repository
                .updateSong(shortSongDtoMapper.mapToObjectFromShort(shortSongDto)));
    }

    @Override
    public void deleteSong(Long id) {
        repository.deleteSong(repository.findById(id));
    }
}