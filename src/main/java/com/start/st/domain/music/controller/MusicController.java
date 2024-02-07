package com.start.st.domain.music.controller;

import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.music.entity.Music;
import com.start.st.domain.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MusicController {
    private final MusicService musicService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/music/{id}")
    public String mbtiInformation(@PathVariable("id") Long id, Model model, Principal principal,
                                  @RequestParam("names") List<String> names) {
        if (!principal.getName().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근권한이 없습니다.");
        }
        Music music = this.musicService.findMusicById(id);
        this.musicService.modify(music, music.getNames(), music.getGenre());
        return "redirect:/";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/genre/music/register")
    public String registerMusicGenre(Principal principal, @RequestParam("musicGenre") String musicGenre) {
        this.musicService.create(musicGenre);
        return "redirect:/genre";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/genre/music/delete")
    public String deleteMusicGenre(@RequestParam("musicId") Long genreId, Principal principal) {
        Music music = this.musicService.findMusicById(genreId);
        this.musicService.deleteMusicGenre(music);
        return "redirect:/genre";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/name/music")
    public String registerMovieName(@RequestParam("musicId") Long genreId, Model model, Principal principal,
                                    @RequestParam("musicNames") List<String> names) {
        Music music = this.musicService.findMusicById(genreId);
        this.musicService.modify(music, names, music.getGenre());
        return "redirect:/genre";
    }
}
