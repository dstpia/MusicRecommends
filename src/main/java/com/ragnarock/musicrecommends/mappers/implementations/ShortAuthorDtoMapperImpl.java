package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.mappers.shortmappers.ShortAuthorDtoMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.repository.SongRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ShortAuthorDtoMapperImpl implements ShortAuthorDtoMapper {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Override
    public ShortAuthorDto mapToShortDto(Author author) {
        ShortAuthorDto shortAuthorDto = new ShortAuthorDto("", "");
        if (author != null) {
            shortAuthorDto.setId(author.getId());
            shortAuthorDto.setName(author.getName());
            shortAuthorDto.setGenre(author.getGenre());
            if (author.getAlbums() != null) {
                shortAuthorDto.setAlbumsId(author.getAlbums().stream()
                        .map(Album::getId).collect(Collectors.toList()));
            }
            if (author.getSongs() != null) {
                shortAuthorDto.setSongsId(author.getSongs().stream()
                        .map(Song::getId).collect(Collectors.toList()));
            }
        }
        return shortAuthorDto;
    }

    @Override
    public Author mapToObjectFromShort(ShortAuthorDto shortAuthorDto) {
        Author author = new Author();
        if (shortAuthorDto != null) {
            author.setId(shortAuthorDto.getId());
            author.setName(shortAuthorDto.getName());
            author.setGenre(shortAuthorDto.getGenre());
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
    public List<ShortAuthorDto> mapToShortDtoList(List<Author> authors) {
        List<ShortAuthorDto> authorsDto = new ArrayList<>();
        if (authors != null) {
            authors.forEach(author -> authorsDto.add(mapToShortDto(author)));
        }
        return authorsDto;
    }

    @Override
    public List<Author> mapToObjectListFromShort(List<ShortAuthorDto> authorsDto) {
        List<Author> authors = new ArrayList<>();
        if (authorsDto != null) {
            authorsDto.forEach(authorDto -> authors.add(mapToObjectFromShort(authorDto)));
        }
        return authors;
    }
}
