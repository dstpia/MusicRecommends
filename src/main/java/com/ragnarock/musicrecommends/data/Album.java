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
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private String name;
    @NotNull
    private String genre;
    private Long year;
    @OneToMany(mappedBy = "album",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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

    public void setYear(Long year) {
        if (year != null && year < 0) {
            throw new IllegalArgumentException("Год не может быть отрицательным");
        }
        this.year = year;
    }
}
