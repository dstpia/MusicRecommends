package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.cache.CustomCache;
import com.ragnarock.musicrecommends.dto.longdto.LongSongDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongSongDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.repository.SongRepository;
import com.ragnarock.musicrecommends.services.SongService;
import java.util.ArrayList;
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
        List<LongSongDto> longSongDtoList = null;
        if (songsCache.notEmptyCheck()) {
            longSongDtoList = songsCache.getValues().stream()
                    .filter(longSongDto -> stringFilter(longSongDto.getName(), name))
                    .filter(longSongDto -> stringFilter(longSongDto.getLyrics(), normalizedLyrics))
                    .sorted(Comparator.comparing(LongSongDto::getId)).toList();
            longSongDtoList.forEach(longSongDto ->
                    songsCache.putInCache(longSongDto.getId(), longSongDto));
            log.info("Found in cache {} songs with searched name and lyrics",
                    longSongDtoList.size());
        }
        if ((longSongDtoList == null) || (longSongDtoList.size()
                != repository.findByNameAndLyrics(name, normalizedLyrics).size())) {
            longSongDtoList = longSongDtoMapper
                    .mapToLongDtoList(repository.findByNameAndLyrics(name, normalizedLyrics));
            longSongDtoList.forEach(longSongDto ->
                    songsCache.putInCache(longSongDto.getId(), longSongDto));
            log.info("Found in repository {} songs with searched name and lyrics",
                    longSongDtoList.size());
        }
        return longSongDtoList;
    }

    @Override
    public List<LongSongDto> findByAlbumYear(Long year) {
        List<LongSongDto> longSongDtoList = null;
        if (songsCache.notEmptyCheck()) {
            longSongDtoList = songsCache.getValues().stream()
                    .filter(longSongDto -> {
                        if (longSongDto.getAlbum() != null) {
                            return Objects.equals(longSongDto.getAlbum().getYear(), year);
                        }
                        return false;
                    })
                    .sorted(Comparator.comparing(LongSongDto::getId)).toList();
            longSongDtoList.forEach(longSongDto ->
                    songsCache.putInCache(longSongDto.getId(), longSongDto));
            log.info("Found in cache {} songs with searched album year", longSongDtoList.size());
        }
        if ((longSongDtoList == null)
                || (longSongDtoList.size() != repository.findByAlbumYear(year).size())) {
            longSongDtoList = longSongDtoMapper.mapToLongDtoList(repository.findByAlbumYear(year));
            longSongDtoList.forEach(longSongDto ->
                    songsCache.putInCache(longSongDto.getId(), longSongDto));
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
        List<LongSongDto> longSongDtoList = null;
        if (songsCache.notEmptyCheck()) {
            longSongDtoList = songsCache.getValues().stream()
                    .filter(longSongDto -> {
                        if (longSongDto.getAlbum() != null) {
                            return longSongDto.getAlbum().getGenre().equals(normalizedGenre);
                        }
                        return false;
                    })
                    .sorted(Comparator.comparing(LongSongDto::getId)).toList();
            longSongDtoList.forEach(longSongDto ->
                    songsCache.putInCache(longSongDto.getId(), longSongDto));
            log.info("Found in cache {} songs with searched album genre",
                    longSongDtoList.size());
        }
        if ((longSongDtoList == null) || (longSongDtoList.size()
                != repository.findByAlbumGenre(normalizedGenre).size())) {
            longSongDtoList = longSongDtoMapper
                    .mapToLongDtoList(repository.findByAlbumGenre(normalizedGenre));
            longSongDtoList.forEach(longSongDto ->
                    songsCache.putInCache(longSongDto.getId(), longSongDto));
            log.info("Found in repository {} songs with searched album genre",
                    longSongDtoList.size());
        }
        return longSongDtoList;
    }

    @Override
    public LongSongDto findById(Long id) {
        LongSongDto longSongDto = null;
        if (songsCache.notEmptyCheck()) {
            longSongDto = songsCache.getFromCache(id);
            if (longSongDto != null) {
                songsCache.putInCache(longSongDto.getId(), longSongDto);
                log.info("Found in cache song with searched id: {}", longSongDto.getId());
            }
        }
        if (longSongDto == null) {
            longSongDto = longSongDtoMapper.mapToLongDto(repository.findById(id).orElse(null));
            if (longSongDto != null && longSongDto.getId() != null) {
                songsCache.putInCache(longSongDto.getId(), longSongDto);
                log.info("Found in repository song with searched id: {}", longSongDto.getId());
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
        } else {
            log.error("Failed to save new song");
        }
        return longSongDto;
    }

    @Override
    @Transactional
    public List<LongSongDto> saveSongsList(List<ShortSongDto> songsList) {
        List<LongSongDto> songs = new ArrayList<>();
        songsList.forEach(authorDto -> {
            LongSongDto longSongDto;
            longSongDto = longSongDtoMapper.mapToLongDto(repository
                    .save(shortSongDtoMapper.mapToObjectFromShort(authorDto)));
            if (longSongDto != null) {
                songs.add(longSongDto);
                songsCache.putInCache(longSongDto.getId(), longSongDto);
                log.info("Saved in cache and repository song, id: {}", longSongDto.getId());
            } else {
                log.error("Object not saved in cache and repository song");
            }
        });
        return songs;
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
            log.error("[404]: Can't update song with searched id: {}", shortSongDto.getId());
        }
        return longSongDto;
    }

    @Override
    @Transactional
    public boolean deleteSong(Long id) {
        if (repository.existsById(id)) {
            if (songsCache.notEmptyCheck()) {
                songsCache.removeFromCache(id);
            }
            repository.deleteById(id);
            log.info("Deleted in cache and repository song, id: {}", id);
            return true;
        } else {
            log.error("[404]: Can't delete song with searched id: {}", id);
            return false;
        }
    }

    public boolean stringFilter(String compareString, String filterString) {
        if (filterString == null) {
            return true;
        }
        if (compareString != null) {
            return compareString.equals(filterString);
        }
        return false;
    }
}
