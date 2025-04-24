package com.ragnarock.musicrecommends.services;

import com.ragnarock.musicrecommends.dto.longdto.LongAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface AlbumService {

    List<LongAlbumDto> findAll();

    List<LongAlbumDto> findByNameAndGenre(String name, String genre);

    List<LongAlbumDto> findByYear(Long year);

    LongAlbumDto findById(Long id);

    LongAlbumDto saveAlbum(ShortAlbumDto album);

    List<LongAlbumDto> saveAlbumsList(List<ShortAlbumDto> albumsList);

    LongAlbumDto updateAlbum(ShortAlbumDto album);

    boolean deleteAlbum(Long id);
}
