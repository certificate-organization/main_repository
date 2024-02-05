package com.start.st.domain.movie.controller;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/movie/{id}")
    public String mbtiInformation(@PathVariable("id") Long id, Model model, Principal principal,
                                  @RequestParam("names") List<String> names) {
        if (!principal.getName().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근권한이 없습니다.");
        }
        Movie movie = this.movieService.findMovieById(id);
        this.movieService.modify(movie, movie.getNames(), movie.getGenre());
        return "redirect:/";
    }
}
