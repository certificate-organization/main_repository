package com.start.st.domain.article.controller;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.entity.ArticleForm;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    private final MemberService memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/mbti/{id}/article/create")
    public String createArticle(@PathVariable("id") Long id, Model model, ArticleForm articleForm, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        Mbti mbti = member.getMbti();
        model.addAttribute("mbti", mbti);
        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/mbti/{id}/article/create")
    public String createArticle(@PathVariable("id") Long id, Model model, Principal principal,
                         @Valid ArticleForm articleForm, BindingResult bindingResult) {
        Member member = this.memberService.getMember(principal.getName());
        Mbti mbti = member.getMbti();
        if (bindingResult.hasErrors()) {
            model.addAttribute("mbti", mbti);
            return "article_form";
        }
        articleService.create(articleForm.getSubject(), articleForm.getContent(), member.getMbti(), member);
        return String.format("redirect:/mbti/%s", id);
    }


    @GetMapping("/article/{id}")
    public String detailArticle(@PathVariable("id") Long id, Model model) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("article", article);
        return "article_detail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/{id}/modify")
    public String modifyArticle(@PathVariable("id") Long id, ArticleForm articleForm, Principal principal) {
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getMembername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        articleForm.setSubject(article.getSubject());
        articleForm.setContent(article.getContent());
        return "article_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article/{id}/modify")
    public String modifyArticle(@PathVariable("id") Long id, Model model, Principal principal,
                                @Valid ArticleForm articleForm, BindingResult bindingResult) {
        Member member = this.memberService.getMember(principal.getName());
        Mbti mbti = member.getMbti();
        if (bindingResult.hasErrors()) {
            model.addAttribute("mbti", mbti);
            return "article_form";
        }
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getMembername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.articleService.modify(article, articleForm.getSubject(), articleForm.getContent());
        return String.format("redirect:/article/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/{id}/delete")
    public String deleteArticle(@PathVariable("id")Long id, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        Mbti mbti = member.getMbti();
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getMembername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.articleService.delete(article);
        return String.format("redirect:/mbti/%s", member.getMbti().getId());
    }
}
