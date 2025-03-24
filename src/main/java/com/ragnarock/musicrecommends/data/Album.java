package com.ragnarock.musicrecommends.data;

import com.ragnarock.musicrecommends.dto.AlbumDto;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Albums")
public class Album {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String genre;
    @Nullable
    private Long year;
    @OneToMany(mappedBy = "album")
    @ToString.Exclude
    @Nullable
    private List<Song> songs;
    @ManyToOne
    @ToString.Exclude
    @Nullable
    private Author author;
}
