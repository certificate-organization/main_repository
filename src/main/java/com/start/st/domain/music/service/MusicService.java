package com.start.st.domain.music.service;

import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.music.entity.Music;
import com.start.st.domain.music.repository.MusicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class MusicService {
    private final MusicRepository musicRepository;

    public void create(String genre) {
        Music music = Music.builder()
                .genre(genre)
                .build();
        this.musicRepository.save(music);
    }

    public void modify(Music music, List<String> names, String genre) {
        Music modifyMusic = music.toBuilder()
                .genre(genre)
                .names(names)
                .build();
        this.musicRepository.save(modifyMusic);
    }

    public List<Music> findAllMusic() {
        return this.musicRepository.findAll();
    }

    public Music findMusicById(Long id) {
        Optional<Music> music = this.musicRepository.findById(id);
        if (music.isEmpty()) {
            return null;
        }
        return music.get();
    }

    public Music findMusicByGenre(String genre) {
        Optional<Music> music = this.musicRepository.findByGenre(genre);
        if (music.isEmpty()) {
            return null;
        }
        return music.get();
    }

    public void deleteMusicGenre(Music music) {
        this.musicRepository.delete(music);
    }

    public Set<Music> getMusicSet(List<Long> musicIds) {
        Set<Music> musicSet = new HashSet<>();
        for (Long id : musicIds) {
            musicSet.add(this.findMusicById(id));
        }
        return musicSet;
    }
}
