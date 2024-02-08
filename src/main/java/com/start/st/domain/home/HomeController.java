package com.start.st.domain.home;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.entity.MemberModifyForm;
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
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MbtiService mbtiService;
    private final ArticleService articleService;
    private final MovieService movieService;
    private final MusicService musicService;
    private final MemberService memberService;

    @GetMapping("/")
    public String root(Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page, MemberModifyForm memberModifyForm) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();

        Page<Article> articlePageByDate = this.articleService.getArticlePageByDate(page);
        Page<Article> articlePageByLike = this.articleService.getArticlePageByLike(page);
        Page<Article> articlePageByView = this.articleService.getArticlePageByView(page);
        model.addAttribute("mbtiList", mbtiList);
        model.addAttribute("articlePageByDate", articlePageByDate);
        model.addAttribute("articlePageByLike", articlePageByLike);
        model.addAttribute("articlePageByView", articlePageByView);
        if (principal != null) {
            Member member = memberService.getMember(principal.getName());
            Page<Article> articleList = this.articleService.getArticlePageByDate(page);
            model.addAttribute("articleList", articleList);
            model.addAttribute("member", member);

            Movie movie = new Movie();
            Music music = new Music();
            movie = this.getRandomMovie(member.getMbti().getMovieList());
            music = this.getRandomMusic(member.getMbti().getMusicList());
            model.addAttribute("movie", movie);
            model.addAttribute("music", music);
            if (memberService.getMember(principal.getName()).getEmail() == null||
                    memberService.getMember(principal.getName()).getEmail().equals("")) {
                List<Mbti> _mbtiList = this.mbtiService.findAllMbti();
                mbtiList.remove(member.getMbti());

                model.addAttribute("member", member);
                model.addAttribute("mbtiList", _mbtiList);
                return "member_modify_form";
            }
            return "mbti_home";
        } else {
            return "mbti_home";
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/genre")
    public String modifyGenre(Model model, Principal principal) {
        if (!principal.getName().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "접근권한이 없습니다.");
        }
        List<Movie> movieList = this.movieService.findAllMovie();
        List<Music> musicList = this.musicService.findAllMusic();
        model.addAttribute("movieList", movieList);
        model.addAttribute("musicList", musicList);
        return "genre_form";
    }

    public Movie getRandomMovie(Set<Movie> movieSet) {
        if (movieSet.isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(movieSet.size());
        int currentIndex = 0;
        for (Movie movie : movieSet) {
            if (currentIndex == randomIndex) {
                return movie;
            }
            currentIndex++;
        }
        return null;
    }

    public Music getRandomMusic(Set<Music> musicSet) {
        if (musicSet.isEmpty()) {
            return null;
        }
        int randomIndex = new Random().nextInt(musicSet.size());
        int currentIndex = 0;
        for (Music music : musicSet) {
            if (currentIndex == randomIndex) {
                return music;
            }
            currentIndex++;
        }
        return null;
    }
}
