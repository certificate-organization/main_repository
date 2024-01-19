package com.start.st.domain.movie.service;

import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public void create(String name) {
        Movie movie = Movie.builder()
                .name(name)
                .build();
        this.movieRepository.save(movie);
    }
}
