package com.start.st.domain.home;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MbtiService mbtiService;
    private final ArticleService articleService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/")
    public String root(String key, Model model, @RequestParam(value = "page", defaultValue = "0") int page, Principal principal) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        Page<Article> articlePageByDate = this.articleService.getArticlePageByDate(page);
        Page<Article> articlePageByLike = this.articleService.getArticlePageByLike(page);
        Page<Article> articlePageByView = this.articleService.getArticlePageByView(page);
        model.addAttribute("mbtiList", mbtiList);
        model.addAttribute("articlePageByDate", articlePageByDate);
        model.addAttribute("articlePageByLike", articlePageByLike);
        model.addAttribute("articlePageByView", articlePageByView);
        return "mbti_home";
    }

    @GetMapping("/test")
    public String test() {

        return "test";
    }
}
