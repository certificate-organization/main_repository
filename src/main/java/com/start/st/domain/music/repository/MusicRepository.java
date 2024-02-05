package com.start.st.domain.music.repository;

import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MusicRepository extends JpaRepository<Music,Long> {
    Optional<Music> findByGenre(String genre);
}
