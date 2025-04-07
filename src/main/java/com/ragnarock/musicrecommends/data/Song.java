package com.ragnarock.musicrecommends.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;

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
    @JoinColumn(name = "album_id")
    @ToString.Exclude
    @Nullable
    private Album album;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "songs_authors",
            joinColumns = @JoinColumn(name = "song_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    @Nullable
    private List<Author> authors;

    @PrePersist
    @PreUpdate
    private void format() {
        if (lyrics != null && !lyrics.isEmpty()) {
            this.lyrics = this.lyrics.substring(0, 1).toUpperCase()
                    + this.lyrics.substring(1).toLowerCase();
        }
    }
}
