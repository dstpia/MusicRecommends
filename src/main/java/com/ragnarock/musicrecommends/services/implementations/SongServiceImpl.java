package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.cache.CustomCache;
import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongSongDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.repository.SongRepository;
import com.ragnarock.musicrecommends.services.SongService;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Primary
public class SongServiceImpl implements SongService {
    private final SongRepository repository;
    private final LongSongDtoMapper longSongDtoMapper;
    private final ShortSongDtoMapper shortSongDtoMapper;
    private final CustomCache<Long, LongSongDto> songsCache = new CustomCache<>();

    @Override
    public List<LongSongDto> findAll() {
        List<LongSongDto> longSongDtoList;
        longSongDtoList = longSongDtoMapper.mapToLongDtoList(repository.findAll());
        longSongDtoList.forEach(longSongDto -> {
            songsCache.putInCache(longSongDto.getId(), longSongDto); });
        log.info("Found in repository {} songs", longSongDtoList.size());
        return longSongDtoList;
    }

    @Override
    public List<LongSongDto> findByNameAndLyrics(String name, String lyrics) {
        String normalizedLyrics;
        if (lyrics != null) {
            normalizedLyrics = lyrics.substring(0, 1).toUpperCase()
                    + lyrics.substring(1).toLowerCase();
        } else {
            normalizedLyrics = null;
        }
        List<LongSongDto> longSongDtoList;
        if (songsCache.notEmptyCheck() && (songsCache.cacheCurrentSize() == repository.count())) {
            longSongDtoList = songsCache.getValues();
            longSongDtoList.stream()
                    .filter(longSongDto -> longSongDto.getName().equals(name))
                    .filter(longSongDto -> longSongDto.getLyrics().equals(normalizedLyrics))
                    .sorted(Comparator.comparing(LongSongDto::getId))
                    .forEach(longSongDto -> {
                        songsCache.putInCache(longSongDto.getId(), longSongDto); });
            log.info("Found in cache {} songs with searched name and lyrics",
                    longSongDtoList.size());
        } else {
            longSongDtoList = longSongDtoMapper
                    .mapToLongDtoList(repository.findByNameAndLyrics(name, normalizedLyrics));
            longSongDtoList.forEach(longSongDto -> {
                songsCache.putInCache(longSongDto.getId(), longSongDto); });
            log.info("Found in repository {} songs with searched name and lyrics",
                    longSongDtoList.size());
        }
        return longSongDtoList;
    }

    @Override
    public List<LongSongDto> findByAlbumYear(Long year) {
        List<LongSongDto> longSongDtoList;
        if (songsCache.notEmptyCheck() && (songsCache.cacheCurrentSize() == repository.count())) {
            longSongDtoList = songsCache.getValues();
            longSongDtoList.stream()
                    .filter(longSongDto -> {
                        if (longSongDto.getAlbum() != null) {
                            return Objects.equals(longSongDto.getAlbum().getYear(), year);
                        }
                        return false;
                    })
                    .sorted(Comparator.comparing(LongSongDto::getId))
                    .forEach(longSongDto -> {
                        songsCache.putInCache(longSongDto.getId(), longSongDto); });
            log.info("Found in cache {} songs with searched album year", longSongDtoList.size());
        } else {
            longSongDtoList = longSongDtoMapper.mapToLongDtoList(repository.findByAlbumYear(year));
            longSongDtoList.forEach(longSongDto -> {
                songsCache.putInCache(longSongDto.getId(), longSongDto); });
            log.info("Found in repository {} songs with searched album year",
                    longSongDtoList.size());
        }
        return longSongDtoList;
    }

    @Override
    public List<LongSongDto> findByAlbumGenre(String genre) {
        String normalizedGenre;
        if (genre != null) {
            normalizedGenre = genre.toLowerCase();
        } else {
            normalizedGenre = null;
        }
        List<LongSongDto> longSongDtoList;
        if (songsCache.notEmptyCheck() && (songsCache.cacheCurrentSize() == repository.count())) {
            longSongDtoList = songsCache.getValues();
            longSongDtoList.stream()
                    .filter(longSongDto -> {
                        if (longSongDto.getAlbum() != null) {
                            longSongDto.getAlbum().getGenre().equals(normalizedGenre);
                        }
                        return false;
                    })
                    .sorted(Comparator.comparing(LongSongDto::getId))
                    .forEach(longSongDto -> {
                        songsCache.putInCache(longSongDto.getId(), longSongDto); });
            log.info("Found in cache {} songs with searched album genre",
                    longSongDtoList.size());
        } else {
            longSongDtoList = longSongDtoMapper
                    .mapToLongDtoList(repository.findByAlbumGenre(normalizedGenre));
            longSongDtoList.forEach(longSongDto -> {
                songsCache.putInCache(longSongDto.getId(), longSongDto); });
            log.info("Found in repository {} songs with searched album genre",
                    longSongDtoList.size());
        }
        return longSongDtoList;
    }

    @Override
    public LongSongDto findById(Long id) {
        LongSongDto longSongDto;
        if (songsCache.notEmptyCheck() && (songsCache.cacheCurrentSize() == repository.count())) {
            longSongDto = songsCache.getFromCache(id);
            if (longSongDto != null) {
                songsCache.putInCache(longSongDto.getId(), longSongDto);
                log.info("Found in cache song with searched id: {}", longSongDto.getId());
            }
        } else {
            longSongDto = longSongDtoMapper.mapToLongDto(repository.findById(id).orElse(null));
            if (longSongDto != null) {
                songsCache.putInCache(longSongDto.getId(), longSongDto);
                log.info("Found in repository song with searched id: {}", id);
            }
        }
        return longSongDto;
    }

    @Override
    @Transactional
    public LongSongDto saveSong(ShortSongDto shortSongDto) {
        LongSongDto longSongDto;
        longSongDto = longSongDtoMapper.mapToLongDto(repository
                .save(shortSongDtoMapper.mapToObjectFromShort(shortSongDto)));
        if (longSongDto != null) {
            songsCache.putInCache(longSongDto.getId(), longSongDto);
            log.info("Saved in cache and repository song, id: {}", longSongDto.getId());
        }
        return longSongDto;
    }

    @Override
    @Transactional
    public LongSongDto updateSong(ShortSongDto shortSongDto) {
        LongSongDto longSongDto;
        if (repository.existsById(shortSongDto.getId())) {
            longSongDto = longSongDtoMapper.mapToLongDto(repository
                    .save(shortSongDtoMapper.mapToObjectFromShort(shortSongDto)));
            songsCache.putInCache(longSongDto.getId(), longSongDto);
            log.info("Updated in cache and repository song, id: {}", longSongDto.getId());
        } else {
            longSongDto = null;
            log.error("Can't update song with searched id: {}", shortSongDto.getId());
        }
        return longSongDto;
    }

    @Override
    @Transactional
    public boolean deleteSong(Long id) {
        if (repository.existsById(id)) {
            if (songsCache.notEmptyCheck() && (songsCache.cacheCurrentSize() == repository.count())) {
                songsCache.removeFromCache(id);
            }
            repository.deleteById(id);
            log.info("Deleted in cache and repository song, id: {}", id);
            return true;
        } else {
            log.error("Can't delete song with searched id: {}", id);
            return false;
        }
    }
}
