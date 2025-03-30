package com.ragnarock.musicrecommends.dto.shortdto;

import jakarta.annotation.Nullable;
import java.util.List;
import lombok.Data;

@Data
public class ShortAuthorDto {
    private Long id;
    private String name;
    private String genre;
    @Nullable
    private List<Long> albumsId;
    @Nullable
    private List<Long> songsId;
}
