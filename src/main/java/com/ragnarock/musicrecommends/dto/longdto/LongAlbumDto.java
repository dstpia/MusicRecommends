package com.ragnarock.musicrecommends.dto.longdto;

import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Data;

@Data
public class LongAlbumDto {
    private Long id;
    private String name;
    private String genre;
    @Nullable
    private Long year;
    @Nullable
    private List<ShortSongDto> songs;
    @Nullable
    private ShortAuthorDto author;
}
