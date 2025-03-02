package com.ragnarock.musicrecommends.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Album {
    private int id;
    private String name;
    private int year;
    private String genre;
    private String recordLabel;
    private Song[] songs;
}
