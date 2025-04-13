package com.ragnarock.musicrecommends.dto.shortdto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class ShortSongDto {
    private Long id;
    @NotNull
    @NonNull
    private String name;
    @NotNull
    @NonNull
    private String lyrics;
    @Nullable
    private Long albumId;
    @Nullable
    private List<Long> authorsId;
}
