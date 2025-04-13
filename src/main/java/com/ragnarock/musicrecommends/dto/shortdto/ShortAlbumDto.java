package com.ragnarock.musicrecommends.dto.shortdto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class ShortAlbumDto {
    private Long id;
    @NotNull
    @NonNull
    private String name;
    @NotNull
    @NonNull
    private String genre;
    private Long year;
    @Nullable
    private List<Long> songsId;
    @Nullable
    private Long authorId;

    public void setYear(Long year) {
        if (year != null && year < 0) {
            throw new IllegalArgumentException("Год не может быть отрицательным");
        }
        this.year = year;
    }
}
