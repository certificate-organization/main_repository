package com.start.st.domain.movie.controller;

import com.start.st.domain.movie.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;
}
