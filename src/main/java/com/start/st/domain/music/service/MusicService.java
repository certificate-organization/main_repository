package com.start.st.domain.music.service;

import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.music.entity.Music;
import com.start.st.domain.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;


    public List<Music> findAll() {
        return this.musicRepository.findAll();
    }
    public void create(String genre) {
        Music music = Music.builder()
                .genre(genre)
                .build();
        this.musicRepository.save(music);
    }

    public List<Music> findAllMusic() {
        return this.musicRepository.findAll();
    }
}
