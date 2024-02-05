package com.start.st.domain.movie.service;

import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public void create(String genre) {
        Movie movie = Movie.builder()
                .genre(genre)
                .build();
        this.movieRepository.save(movie);
    }

    public List<Movie> findAllMovie() {
        return this.movieRepository.findAll();
    }

    public Movie findMovieByGenre(String genre) {
        Optional<Movie> movie = this.movieRepository.findByGenre(genre);
        if (movie.isEmpty()) {
            return null;
        }
        return movie.get();
    }
}
