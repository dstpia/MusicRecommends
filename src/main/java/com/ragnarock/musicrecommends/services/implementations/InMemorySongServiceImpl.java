package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.SongDto;
import com.ragnarock.musicrecommends.mappers.SongMapper;
import com.ragnarock.musicrecommends.repository.InMemorySongDao;
import com.ragnarock.musicrecommends.services.SongService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class InMemorySongServiceImpl implements SongService {
    private final InMemorySongDao repository;
    private final SongMapper mapper;

    @Override
    public List<SongDto> findAll() {
        return mapper.mapToDtoList(repository.findAll());
    }

    @Override
    public List<SongDto> findByNameAndLyrics(String name, String lyrics) {
        return mapper.mapToDtoList(repository.findByNameAndLyrics(name, lyrics));
    }

    @Override
    public SongDto findById(Long id) {
        return mapper.mapToDto(repository.findById(id));
    }

    @Override
    public SongDto saveSong(SongDto songDto) {
        return mapper.mapToDto(repository.saveSong(mapper.mapToObject(songDto)));
    }

    @Override
    public SongDto updateSong(SongDto songDto) {
        return mapper.mapToDto(repository.updateSong(mapper.mapToObject(songDto)));
    }

    @Override
    public void deleteSong(Long id) {
        repository.deleteSong(repository.findById(id));
    }
}