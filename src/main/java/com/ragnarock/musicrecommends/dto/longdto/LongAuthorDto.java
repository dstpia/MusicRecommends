package com.ragnarock.musicrecommends.dto.longdto;

import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Data;

@Data
public class LongAuthorDto {
    private Long id;
    private String name;
    private String genre;
    @Nullable
    private List<ShortAlbumDto> albums;
    @Nullable
    private List<ShortSongDto> songs;
}
