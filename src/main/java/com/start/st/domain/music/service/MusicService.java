package com.start.st.domain.music.service;

import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.music.entity.Music;
import com.start.st.domain.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;

    public void create(String name) {
        Music music = Music.builder()
                .name(name)
                .build();
        this.musicRepository.save(music);
    }
}
