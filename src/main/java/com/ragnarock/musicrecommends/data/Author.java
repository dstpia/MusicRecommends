package com.ragnarock.musicrecommends.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {
    private int id;
    private String name;
    private String genre;
    private Album[] albums;
}
