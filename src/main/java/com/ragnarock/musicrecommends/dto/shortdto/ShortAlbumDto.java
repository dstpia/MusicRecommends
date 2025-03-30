package com.ragnarock.musicrecommends.dto.shortdto;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Data;

@Data
public class ShortAlbumDto {
    private Long id;
    private String name;
    private String genre;
    @Nullable
    private Long year;
    @Nullable
    private List<Long> songsId;
    @Nullable
    private Long authorId;
}
