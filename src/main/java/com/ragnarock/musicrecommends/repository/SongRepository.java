package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Song;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s FROM Song s WHERE "
            + "(:name IS NULL OR s.name = :name) AND "
            + "(:lyrics IS NULL OR s.lyrics = :lyrics)")
    List<Song> findByNameAndLyrics(@Param("name") String name, @Param("lyrics") String lyrics);
}
