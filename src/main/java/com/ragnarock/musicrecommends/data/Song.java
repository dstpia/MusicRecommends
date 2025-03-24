package com.ragnarock.musicrecommends.data;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "Songs")
public class Song {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lyrics;
    @ManyToOne
    @ToString.Exclude
    @Nullable
    private Album album;
    @ManyToMany
    @JoinTable(
            name = "songs_authors",
            joinColumns = @JoinColumn(name = "songId"),
            inverseJoinColumns = @JoinColumn(name = "authorId")
    )
    @ToString.Exclude
    @Nullable
    private List<Author> authors;
}
