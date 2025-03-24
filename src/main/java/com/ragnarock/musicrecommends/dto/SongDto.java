package com.ragnarock.musicrecommends.dto;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Data;

@Data
public class SongDto {
    private Long id;
    private String name;
    private String lyrics;
    @Nullable
    private Long album_id;
    @Nullable
    private List<Long> authors_id;
}
