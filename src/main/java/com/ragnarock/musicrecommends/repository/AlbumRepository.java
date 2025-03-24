package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Album;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AlbumRepository extends JpaRepository<Album, Long> {
    @Query("SELECT s FROM Album s WHERE "
            + "(:name IS NULL OR s.name = :name) AND "
            + "(:genre IS NULL OR s.genre = :genre)")
    List<Album> findByNameAndGenre(@Param("name") String name, @Param("genre") String genre);

    @Query("SELECT s FROM Album s WHERE "
            + "(:year IS NULL OR s.year = :year)")
    List<Album> findByYear(@Param("year") Long year);
}
