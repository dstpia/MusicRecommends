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
    private Long albumId;
    @Nullable
    private List<Long> authorsId;
}
