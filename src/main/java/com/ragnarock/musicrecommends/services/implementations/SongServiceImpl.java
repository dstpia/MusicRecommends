package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.dto.SongDto;
import com.ragnarock.musicrecommends.mappers.SongMapper;
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
        return mapper.mapToDto(repository.findById(id).orElse(null));
    }

    @Override
    public SongDto saveSong(SongDto songDto) {
        return mapper.mapToDto(repository.save(mapper.mapToObject(songDto)));
    }

    @Override
    public SongDto updateSong(SongDto songDto) {
        return mapper.mapToDto(repository.save(mapper.mapToObject(songDto)));
    }

    @Override
    @Transactional
    public void deleteSong(Long id) {
        repository.deleteById(id);
    }
}
