package com.start.st.domain.music.controller;

import com.start.st.domain.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;
}
