package com.ragnarock.musicrecommends.dto.longdto;

import com.ragnarock.musicrecommends.dto.shortdto.ShortAlbumDto;
import com.ragnarock.musicrecommends.dto.shortdto.ShortSongDto;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;

@Data
public class LongAuthorDto {
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private String genre;
    @Nullable
    private List<ShortAlbumDto> albums;
    @Nullable
    private List<ShortSongDto> songs;
}
