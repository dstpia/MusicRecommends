package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Album;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("SELECT a FROM Album a"
            + " WHERE (:name IS NULL OR a.name = :name)"
            + " AND (:genre IS NULL OR a.genre = :genre)")
    List<Album> findByNameAndGenre(@Param("name") String name, @Param("genre") String genre);

    @Query("SELECT a FROM Album a WHERE (:year IS NULL OR a.year = :year)")
    List<Album> findByYear(@Param("year") Long year);
}
