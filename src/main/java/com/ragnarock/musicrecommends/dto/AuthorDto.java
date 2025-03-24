package com.ragnarock.musicrecommends.dto;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Data;

@Data
public class AuthorDto {
    private Long id;
    private String name;
    private String genre;
    @Nullable
    private List<Long> albums_id;
    @Nullable
    private List<Long> songs_id;
}
