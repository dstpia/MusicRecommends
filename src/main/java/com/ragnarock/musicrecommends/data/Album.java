package com.ragnarock.musicrecommends.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;

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
    @OneToMany(mappedBy = "album",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            orphanRemoval = true)
    @ToString.Exclude
    @Nullable
    private List<Song> songs;
    @ManyToOne
    @JoinColumn(name = "author_id")
    @ToString.Exclude
    @Nullable
    private Author author;

    @PrePersist
    @PreUpdate
    private void format() {
        if (this.genre != null && !this.genre.isEmpty()) {
            this.genre = this.genre.toLowerCase();
        }
    }
}
