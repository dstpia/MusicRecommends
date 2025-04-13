package com.ragnarock.musicrecommends.services.implementations;

import com.ragnarock.musicrecommends.cache.CustomCache;
import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAuthorDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAuthorDtoMapper;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import com.ragnarock.musicrecommends.services.AuthorService;
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
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    private final LongAuthorDtoMapper longAuthorDtoMapper;
    private final ShortAuthorDtoMapper shortAuthorDtoMapper;
    private final CustomCache<Long, LongAuthorDto> authorCache = new CustomCache<>();

    @Override
    public List<LongAuthorDto> findAll() {
        List<LongAuthorDto> longAuthorDtoList;
        longAuthorDtoList = longAuthorDtoMapper.mapToLongDtoList(repository.findAll());
        log.info("Found in repository {} authors", longAuthorDtoList.size());
        return longAuthorDtoList;
    }

    @Override
    public List<LongAuthorDto> findByNameAndGenre(String name, String genre) {
        String normalizedGenre;
        if (genre != null) {
            normalizedGenre = genre.toLowerCase();
        } else {
            normalizedGenre = null;
        }
        List<LongAuthorDto> longAuthorDtoList = null;
        if (authorCache.notEmptyCheck() && (authorCache.cacheCurrentSize() == repository.count())) {
            longAuthorDtoList = authorCache.getValues().stream()
                    .filter(authorDto -> {
                        if (name == null) {
                            return true;
                        }
                        if (authorDto.getName() != null) {
                            return authorDto.getName().equals(name);
                        }
                        return false;
                    })
                    .filter(authorDto -> {
                        if (normalizedGenre == null) {
                            return true;
                        }
                        if (authorDto.getGenre() != null) {
                            return authorDto.getGenre().equals(normalizedGenre);
                        }
                        return false;
                    })
                    .sorted(Comparator.comparing(LongAuthorDto::getId)).toList();
            longAuthorDtoList.forEach(authorDto -> {
                authorCache.putInCache(authorDto.getId(), authorDto); });
            log.info("Found in cache {} authors with searched name and genre",
                    longAuthorDtoList.size());
        }
        if ((longAuthorDtoList != null) && (longAuthorDtoList.size()
                != repository.findByNameAndGenre(name, genre).size())) {
            longAuthorDtoList = longAuthorDtoMapper
                    .mapToLongDtoList(repository.findByNameAndGenre(name, normalizedGenre));
            longAuthorDtoList.forEach(longAuthorDto -> {
                authorCache.putInCache(longAuthorDto.getId(), longAuthorDto); });
            log.info("Found in repository {} authors with searched name and genre",
                    longAuthorDtoList.size());
        }
        return longAuthorDtoList;
    }

    @Override
    public LongAuthorDto findById(Long id) {
        LongAuthorDto longAuthorDto = null;
        if (authorCache.notEmptyCheck() && (authorCache.cacheCurrentSize() == repository.count())) {
            longAuthorDto = authorCache.getFromCache(id);
            if (longAuthorDto != null) {
                authorCache.putInCache(longAuthorDto.getId(), longAuthorDto);
                log.info("Found in cache author with searched id: {}", longAuthorDto.getId());
            }
        }
        if (longAuthorDto == null) {
            longAuthorDto = longAuthorDtoMapper.mapToLongDto(repository.findById(id).orElse(null));
            if (longAuthorDto != null) {
                authorCache.putInCache(longAuthorDto.getId(), longAuthorDto);
                log.info("Found in repository author with searched id: {}", longAuthorDto.getId());
            }
        }
        return longAuthorDto;
    }

    @Override
    @Transactional
    public LongAuthorDto saveAuthor(ShortAuthorDto shortAuthorDto) {
        LongAuthorDto longAuthorDto;
        longAuthorDto = longAuthorDtoMapper.mapToLongDto(repository
                .save(shortAuthorDtoMapper.mapToObjectFromShort(shortAuthorDto)));
        if (longAuthorDto != null) {
            authorCache.putInCache(longAuthorDto.getId(), longAuthorDto);
            log.info("Saved in cache and repository author, id: {}", longAuthorDto.getId());
        } else {
            log.error("Failed to save new author");
        }
        return longAuthorDto;
    }

    @Override
    @Transactional
    public LongAuthorDto updateAuthor(ShortAuthorDto shortAuthorDto) {
        LongAuthorDto longAuthorDto;
        if (repository.existsById(shortAuthorDto.getId())) {
            longAuthorDto = longAuthorDtoMapper.mapToLongDto(repository
                    .save(shortAuthorDtoMapper.mapToObjectFromShort(shortAuthorDto)));
            authorCache.putInCache(longAuthorDto.getId(), longAuthorDto);
            log.info("Updated in cache and repository author, id: {}", longAuthorDto.getId());
        } else {
            longAuthorDto = null;
            log.error("[404]: Can't update author with searched id: {}", shortAuthorDto.getId());
        }
        return longAuthorDto;
    }

    @Override
    @Transactional
    public boolean deleteAuthor(Long id) {
        if (repository.existsById(id)) {
            if (authorCache.notEmptyCheck()) {
                authorCache.removeFromCache(id);
            }
            repository.deleteById(id);
            log.info("Deleted in cache and repository author, id: {}", id);
            return true;
        } else {
            log.error("[404]: Can't delete author with searched id: {}", id);
            return false;
        }
    }
}
