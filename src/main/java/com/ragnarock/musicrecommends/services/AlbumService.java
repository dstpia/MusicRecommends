package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.AlbumDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AlbumService {

    List<AlbumDto> findAll();

    List<AlbumDto> findByNameAndGenre(String name, String genre);

    AlbumDto findById(Long id);

    AlbumDto saveAlbum(AlbumDto album);

    AlbumDto updateAlbum(AlbumDto album);

    void deleteAlbum(Long id);

    List<AlbumDto> findByYear(Long year);
}
