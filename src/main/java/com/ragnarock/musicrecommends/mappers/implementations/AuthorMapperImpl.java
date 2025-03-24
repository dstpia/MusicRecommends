package com.ragnarock.musicrecommends.mappers.implementations;

import com.ragnarock.musicrecommends.data.Album;
import com.ragnarock.musicrecommends.data.Author;
import com.ragnarock.musicrecommends.data.Song;
import com.ragnarock.musicrecommends.dto.AuthorDto;
import com.ragnarock.musicrecommends.mappers.AuthorMapper;
import com.ragnarock.musicrecommends.repository.AlbumRepository;
import com.ragnarock.musicrecommends.repository.SongRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AuthorMapperImpl implements AuthorMapper {
    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;

    @Override
    public AuthorDto mapToDto(Author author) {
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(author.getId());
        authorDto.setName(author.getName());
        authorDto.setGenre(author.getGenre());
        if (author.getAlbums() != null) {
            authorDto.setAlbums_id(author.getAlbums().stream()
                    .map(Album::getId).collect(Collectors.toList()));
        }
        if (author.getSongs() != null) {
            authorDto.setSongs_id(author.getSongs().stream()
                    .map(Song::getId).collect(Collectors.toList()));
        }
        return authorDto;
    }

    @Override
    public Author mapToObject(AuthorDto authorDto) {
        Author author = new Author();
        author.setId(authorDto.getId());
        author.setName(authorDto.getName());
        author.setGenre(authorDto.getGenre());
        if (authorDto.getAlbums_id() != null) {
            author.setAlbums(albumRepository.findAllById(authorDto.getAlbums_id()));
        }
        if (authorDto.getSongs_id() != null) {
            author.setSongs(songRepository.findAllById(authorDto.getSongs_id()));
        }
        return author;
    }

    @Override
    public List<AuthorDto> mapToDtoList(List<Author> authors) {
        List<AuthorDto> authorsDto = new ArrayList<>();
        authors.forEach(author -> authorsDto.add(mapToDto(author)));
        return authorsDto;
    }

    @Override
    public List<Author> mapToObjectList(List<AuthorDto> authorsDto) {
        List<Author> authors = new ArrayList<>();
        authorsDto.forEach(authorDto -> authors.add(mapToObject(authorDto)));
        return authors;
    }
}
