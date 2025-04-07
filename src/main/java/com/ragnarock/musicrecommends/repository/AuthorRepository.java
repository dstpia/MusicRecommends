package com.ragnarock.musicrecommends.repository;

import com.ragnarock.musicrecommends.data.Author;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    @Query("SELECT a FROM Author a"
            + " WHERE (:name IS NULL OR a.name = :name)"
            + " AND (:genre IS NULL OR a.genre = :genre)")
    List<Author> findByNameAndGenre(@Param("name") String name, @Param("genre") String genre);
}
