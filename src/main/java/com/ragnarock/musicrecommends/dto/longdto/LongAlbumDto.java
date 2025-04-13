package com.ragnarock.musicrecommends.dto.longdto;

import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class LongAlbumDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String genre;
    private Long year;
    @Nullable
    private List<ShortSongDto> songs;
    @Nullable
    private ShortAuthorDto author;

    @PrePersist
    @PreUpdate
    private void format() {
        if (this.genre != null && !this.genre.isEmpty()) {
            this.genre = this.genre.toLowerCase();
        }
    }

    public void setYear(Long year) {
        if (year != null && year < 0) {
            throw new IllegalArgumentException("Год не может быть отрицательным");
        }
        this.year = year;
    }
}
