package com.ragnarock.musicrecommends.data;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

@Data
@Entity
@Table(name = "Authors")
public class Author {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String genre;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Nullable
    private List<Album> albums;
    @ManyToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "songs_authors",
            joinColumns = @JoinColumn(name = "author_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id")
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    @ToString.Exclude
    @Nullable
    private List<Song> songs;

    @PrePersist
    @PreUpdate
    private void format() {
        if (this.genre != null && !this.genre.isEmpty()) {
            this.genre = this.genre.toLowerCase();
        }
    }
}
