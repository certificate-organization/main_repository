package com.start.st.domain.mbti.controller;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.entity.MemberSignupForm;
import com.start.st.domain.member.service.MemberService;
import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.movie.service.MovieService;
import com.start.st.domain.music.entity.Music;
import com.start.st.domain.music.service.MusicService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/mbti")
public class MbtiController {
    private final MbtiService mbtiService;
    private final MovieService movieService;
    private final MusicService musicService;
    private final MemberService memberService;
    private final ArticleService articleService;

    @GetMapping("/{id}")
    public String mbtiDetail(@PathVariable("id") Long mbtiId, Model model, Principal principal,
                             @RequestParam(value = "page", defaultValue = "0") int page,
                             @RequestParam(value = "keyword", defaultValue = "") String keyword) {
        Page<Article> articlePage = this.articleService.getArticlePageByMbti(keyword, mbtiId, page);
        model.addAttribute("articlePage", articlePage);
        model.addAttribute("keyword", keyword);
        Mbti mbti = this.mbtiService.getMbti(mbtiId);
        model.addAttribute("mbti", mbti);
        if (principal != null) {
            Member member = this.memberService.getMember(principal.getName());
            model.addAttribute("member", member);
        }
        return "mbti_detail";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/information")
    public String mbtiInformation(Model model, Principal principal) {
        if (!principal.getName().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근권한이 없습니다.");
        }
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        List<Movie> movieList = this.movieService.findAllMovie();
        List<Music> musicList = this.musicService.findAllMusic();
        model.addAttribute("mbtiList", mbtiList);
        model.addAttribute("movieList", movieList);
        model.addAttribute("musicList", musicList);
        return "information_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/information/{id}")
    public String mbtiInformation(@PathVariable("id") Long id, Model model, Principal principal,
                                  @RequestParam("love") String love, @RequestParam("relationship") String relationship,
                                  @RequestParam("celebrity") String celebrity, @RequestParam("job") String job) {
        if (!principal.getName().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근권한이 없습니다.");
        }
        Mbti mbti = this.mbtiService.getMbti(id);
        this.mbtiService.modify(mbti, love, relationship, celebrity, job);
        return String.format("redirect:/mbti/%s", id);
    }
}
