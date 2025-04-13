package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.cache.CustomCache;
import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAlbumDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAlbumDtoMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.services.AlbumService;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Primary
public class AlbumServiceImpl implements AlbumService {
    private final AlbumRepository repository;
    private final LongAlbumDtoMapper longAlbumDtoMapper;
    private final ShortAlbumDtoMapper shortAlbumDtoMapper;
    private final CustomCache<Long, LongAlbumDto> albumCache = new CustomCache<>();

    @Override
    public List<LongAlbumDto> findAll() {
        List<LongAlbumDto> longAlbumDtoList;
        longAlbumDtoList = longAlbumDtoMapper.mapToLongDtoList(repository.findAll());
        log.info("Found in repository {} albums", longAlbumDtoList.size());
        return longAlbumDtoList;
    }

    @Override
    public List<LongAlbumDto> findByNameAndGenre(String name, String genre) {
        String normalizedGenre;
        if (genre != null) {
            normalizedGenre = genre.toLowerCase();
        } else {
            normalizedGenre = null;
        }
        List<LongAlbumDto> longAlbumDtoList = null;
        if (albumCache.notEmptyCheck() && (albumCache.cacheCurrentSize() == repository.count())) {
            longAlbumDtoList = albumCache.getValues().stream()
                    .filter(longAlbumDto -> {
                        if (name != null) {
                            if (longAlbumDto.getName() != null) {
                                return longAlbumDto.getName().equals(name);
                            }
                            return false;
                        }
                        return true;
                    })
                    .filter(longAlbumDto -> {
                        if (normalizedGenre != null) {
                            if (longAlbumDto.getGenre() != null) {
                                return longAlbumDto.getGenre().equals(normalizedGenre);
                            }
                            return false;
                        }
                        return true;
                    })
                    .sorted(Comparator.comparing(LongAlbumDto::getId)).toList();
            longAlbumDtoList.forEach(longAlbumDto -> {
                albumCache.putInCache(longAlbumDto.getId(), longAlbumDto); });
            log.info("Found in cache {} albums with searched name and genre",
                    longAlbumDtoList.size());
        }
        if ((longAlbumDtoList != null) && (longAlbumDtoList.size()
                != repository.findByNameAndGenre(name, normalizedGenre).size())) {
            longAlbumDtoList = longAlbumDtoMapper
                    .mapToLongDtoList(repository.findByNameAndGenre(name, normalizedGenre));
            longAlbumDtoList.forEach(longAlbumDto -> {
                albumCache.putInCache(longAlbumDto.getId(), longAlbumDto); });
            log.info("Found in repository {} albums with searched name and genre",
                    longAlbumDtoList.size());
        }
        if (longAlbumDtoList != null) {
            log.info("Found in cache {} albums", longAlbumDtoList.size());
        }
        return longAlbumDtoList;
    }

    @Override
    public List<LongAlbumDto> findByYear(Long year) {
        List<LongAlbumDto> longAlbumDtoList = null;
        if (albumCache.notEmptyCheck() && (albumCache.cacheCurrentSize() == repository.count())) {
            longAlbumDtoList = albumCache.getValues().stream()
                    .filter(longAlbumDto -> {
                        if (longAlbumDto.getYear() != null) {
                            return longAlbumDto.getYear().equals(year);
                        }
                        return false;
                    })
                    .sorted(Comparator.comparing(LongAlbumDto::getId)).toList();
            longAlbumDtoList.forEach(longAlbumDto -> {
                albumCache.putInCache(longAlbumDto.getId(), longAlbumDto); });
            log.info("Found in cache {} albums with searched year", longAlbumDtoList.size());
        }
        if ((longAlbumDtoList != null)
                && (longAlbumDtoList.size() != repository.findByYear(year).size())) {
            longAlbumDtoList = longAlbumDtoMapper.mapToLongDtoList(repository.findByYear(year));
            longAlbumDtoList.forEach(longAlbumDto -> {
                albumCache.putInCache(longAlbumDto.getId(), longAlbumDto); });
            log.info("Found in repository {} albums with searched year", longAlbumDtoList.size());
        }
        if (longAlbumDtoList != null) {
            log.info("Final count of albums: {}", longAlbumDtoList.size());
        }
        return longAlbumDtoList;
    }

    @Override
    public LongAlbumDto findById(Long id) {
        LongAlbumDto longAlbumDto = null;
        if (albumCache.notEmptyCheck() && (albumCache.cacheCurrentSize() == repository.count())) {
            longAlbumDto = albumCache.getFromCache(id);
            if (longAlbumDto != null) {
                albumCache.putInCache(longAlbumDto.getId(), longAlbumDto);
                log.info("Found in cache album with searched id: {}",
                        longAlbumDto.getId());
            }
        }
        if (longAlbumDto == null) {
            longAlbumDto = longAlbumDtoMapper.mapToLongDto(repository.findById(id).orElse(null));
            if (longAlbumDto != null) {
                albumCache.putInCache(longAlbumDto.getId(), longAlbumDto);
                log.info("Found in repository album with searched id: {}", longAlbumDto.getId());
            }
        }
        return longAlbumDto;
    }

    @Override
    @Transactional
    public LongAlbumDto saveAlbum(ShortAlbumDto shortAlbumDto) {
        LongAlbumDto longAlbumDto;
        longAlbumDto = longAlbumDtoMapper.mapToLongDto(repository
                .save(shortAlbumDtoMapper.mapToObjectFromShort(shortAlbumDto)));
        if (longAlbumDto != null) {
            albumCache.putInCache(longAlbumDto.getId(), longAlbumDto);
            log.info("Saved in cache and repository album, id: {}", longAlbumDto.getId());
        }
        return longAlbumDto;
    }

    @Override
    @Transactional
    public LongAlbumDto updateAlbum(ShortAlbumDto shortAlbumDto) {
        LongAlbumDto longAlbumDto;
        if (repository.existsById(shortAlbumDto.getId())) {
            longAlbumDto = longAlbumDtoMapper.mapToLongDto(repository
                    .save(shortAlbumDtoMapper.mapToObjectFromShort(shortAlbumDto)));
            albumCache.putInCache(longAlbumDto.getId(), longAlbumDto);
            log.info("Updated in cache and repository album, id: {}", longAlbumDto.getId());
        } else {
            longAlbumDto = null;
            log.error("[404]: Can't update album with searched id: {}", shortAlbumDto.getId());
        }
        return longAlbumDto;
    }

    @Override
    @Transactional
    public boolean deleteAlbum(Long id) {
        if (repository.existsById(id)) {
            if (albumCache.notEmptyCheck()) {
                albumCache.removeFromCache(id);
            }
            repository.deleteById(id);
            log.info("Deleted in cache and repository album, id: {}", id);
            return true;
        } else {
            log.error("[404]: Can't delete album with searched id: {}", id);
            return false;
        }
    }
}