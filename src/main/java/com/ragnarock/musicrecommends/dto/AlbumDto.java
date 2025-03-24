package com.ragnarock.musicrecommends.dto;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Data;

@Data
public class AlbumDto {
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
