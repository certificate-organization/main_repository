package com.start.st.domain.movie.service;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public void createTest(String genre, List<String> names) {
        Movie movie = Movie.builder()
                .genre(genre)
                .names(names)
                .build();
        this.movieRepository.save(movie);
    }
    public void modify(Movie movie, List<String> names, String genre) {
        Movie modifyMovie = movie.toBuilder()
                .genre(genre)
                .names(names)
                .build();
        this.movieRepository.save(modifyMovie);
    }

    public List<Movie> findAllMovie() {
        return this.movieRepository.findAll();
    }

    public Movie findMovieById(Long id) {
        Optional<Movie> movie = this.movieRepository.findById(id);
        if (movie.isEmpty()) {
            return null;
        }
        return movie.get();
    }

    public Movie findMovieByGenre(String genre) {
        Optional<Movie> movie = this.movieRepository.findByGenre(genre);
        if (movie.isEmpty()) {
            return null;
        }
        return movie.get();
    }

    public void deleteMovieGenre(Movie movie) {
        this.movieRepository.delete(movie);
    }

    public Set<Movie> getMovieSet(List<Long> movieIds) {
        Set<Movie> movieSet = new HashSet<>();
        for (Long id : movieIds) {
            movieSet.add(this.findMovieById(id));
        }
        return movieSet;
    }
}
