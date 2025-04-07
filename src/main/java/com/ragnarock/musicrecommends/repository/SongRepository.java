package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s FROM Song s"
            + " WHERE (:name IS NULL OR s.name = :name)"
            + " AND (:lyrics IS NULL OR s.lyrics = :lyrics)")
    List<Song> findByNameAndLyrics(@Param("name") String name, @Param("lyrics") String lyrics);

    @Query("SELECT s FROM Song s"
            + " LEFT JOIN s.album a WHERE (:year IS NULL"
            + " OR (a IS NOT NULL AND a.year = :year))")
    List<Song> findByAlbumYear(@Param("year") Long year);

    @Query("SELECT s FROM Song s"
            + " LEFT JOIN s.album a"
            + " WHERE (:genre IS NULL OR a.genre = :genre)")
    List<Song> findByAlbumGenre(@Param("genre") String genre);

}
