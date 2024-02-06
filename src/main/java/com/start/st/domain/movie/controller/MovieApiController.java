package com.start.st.domain.movie.controller;

import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/movie")
public class MovieApiController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{genre}")
    public ResponseEntity<Movie> getMovieByGenre(@PathVariable String genre) {
        Movie movie = movieService.findMovieByGenre(genre);

        if (movie != null) {
            return new ResponseEntity<>(movie, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}