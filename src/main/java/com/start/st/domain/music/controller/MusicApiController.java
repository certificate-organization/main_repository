package com.start.st.domain.music.controller;

import com.start.st.domain.music.entity.Music;
import com.start.st.domain.music.service.MusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/music")
public class MusicApiController {

    @Autowired
    private MusicService musicService;

    @GetMapping("/{genre}")
    public ResponseEntity<Music> getMusicByGenre(@PathVariable String genre) {
        Music music = musicService.findMusicByGenre(genre);

        if (music != null) {
            return new ResponseEntity<>(music, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
