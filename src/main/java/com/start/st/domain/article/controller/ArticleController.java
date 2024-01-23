package com.start.st.domain.article.controller;

import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final MbtiService mbtiService;

    @GetMapping("/mbti/{id}/article/create")
    public String create(@PathVariable("id") Long id, Model model) {
        Mbti mbti = this.mbtiService.getMbti(id);
        model.addAttribute("mbti", mbti);
        return "article_form";
    }

    @PostMapping("/mbti/{id}/article/create")
    public String create(@PathVariable("id") Long id, Model model, @RequestParam("subject") String subject, @RequestParam("content") String content) {
        Mbti mbti = this.mbtiService.getMbti(id);
        articleService.create(subject, content, mbti);

        return String.format("redirect:/mbti/%s",id);
    }
}
