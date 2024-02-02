package com.start.st.domain.home;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MbtiService mbtiService;
    private final ArticleService articleService;

    @GetMapping("/")
    public String root(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        Page<Article> articleList = this.articleService.getArticlePageByDate(page);
        model.addAttribute("mbtiList", mbtiList);
        model.addAttribute("articleList",articleList);
        return "mbti_home";
    }

    @GetMapping("/test")
    public String test() {

        return "test";
    }
}
