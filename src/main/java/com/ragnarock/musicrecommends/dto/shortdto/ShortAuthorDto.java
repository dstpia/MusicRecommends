package com.ragnarock.musicrecommends.dto.shortdto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class ShortAuthorDto {
    private Long id;
    @NotNull
    @NonNull
    private String name;
    @NotNull
    @NonNull
    private String genre;
    @Nullable
    private List<Long> albumsId;
    @Nullable
    private List<Long> songsId;
}
