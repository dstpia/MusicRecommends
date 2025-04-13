package com.ragnarock.musicrecommends.dto.longdto;

import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortAuthorDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class LongSongDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String lyrics;
    @Nullable
    private ShortAlbumDto album;
    @Nullable
    private List<ShortAuthorDto> authors;
}
