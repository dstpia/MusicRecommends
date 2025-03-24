package com.ragnarock.musicrecommends.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String genre;
    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    @Nullable
    private List<Album> albums;
    @ManyToMany
    @JoinTable(
            name = "songs_authors",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    @ToString.Exclude
    @Nullable
    private List<Song> songs;
}
