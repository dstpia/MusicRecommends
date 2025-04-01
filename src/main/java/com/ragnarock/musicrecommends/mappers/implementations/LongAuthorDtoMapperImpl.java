package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.dto.longdto.LongAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.mappers.longmappers.LongAuthorDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAlbumDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAuthorDtoMapper;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortSongDtoMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.repository.AuthorRepository;
import com.ragnarock.musicrecommends.repository.SongRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class LongAuthorDtoMapperImpl implements LongAuthorDtoMapper {
    private final AlbumRepository albumRepository;
    private final AuthorRepository authorRepository;
    private final SongRepository songRepository;
    private final ShortAlbumDtoMapper shortAlbumDtoMapper;
    private final ShortSongDtoMapper shortSongDtoMapper;
    private final ShortAuthorDtoMapper shortAuthorDtoMapper;

    @Override
    public LongAuthorDto mapToLongDto(Author author) {
        LongAuthorDto longAuthorDto = new LongAuthorDto();
        if (author != null) {
            longAuthorDto.setId(author.getId());
            longAuthorDto.setName(author.getName());
            longAuthorDto.setGenre(author.getGenre());
            longAuthorDto.setAlbums(shortAlbumDtoMapper.mapToShortDtoList(author.getAlbums()));
            longAuthorDto.setSongs(shortSongDtoMapper.mapToShortDtoList(author.getSongs()));
        }
        return longAuthorDto;
    }

    @Override
    public Author mapToObjectFromLong(LongAuthorDto longAuthorDto) {
        Author author = new Author();
        if (longAuthorDto != null) {
            author.setId(longAuthorDto.getId());
            author.setName(longAuthorDto.getName());
            author.setGenre(longAuthorDto.getGenre());
            ShortAuthorDto shortAuthorDto = shortAuthorDtoMapper.mapToShortDto(authorRepository
                    .getReferenceById(longAuthorDto.getId()));
            if (shortAuthorDto.getAlbumsId() != null) {
                author.setAlbums(albumRepository.findAllById(shortAuthorDto.getAlbumsId()));
            }
            if (shortAuthorDto.getSongsId() != null) {
                author.setSongs(songRepository.findAllById(shortAuthorDto.getSongsId()));
            }
        }
        return author;
    }

    @Override
    public List<LongAuthorDto> mapToLongDtoList(List<Author> authors) {
        List<LongAuthorDto> authorsDto = new ArrayList<>();
        if (authors != null) {
            authors.forEach(author -> authorsDto.add(mapToLongDto(author)));
        }
        return authorsDto;
    }

    @Override
    public List<Author> mapToObjectListFromLong(List<LongAuthorDto> authorsDto) {
        List<Author> authors = new ArrayList<>();
        if (authorsDto != null) {
            authorsDto.forEach(authorDto -> authors.add(mapToObjectFromLong(authorDto)));
        }
        return authors;
    }
}
