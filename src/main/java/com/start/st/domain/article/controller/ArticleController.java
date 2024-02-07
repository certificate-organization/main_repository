package com.start.st.domain.article.controller;

import com.start.st.domain.article.ReportArticleForm.ReportArticleForm;
import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.entity.ArticleForm;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.comment.form.CommentForm;
import com.start.st.domain.comment.form.ReportCommentForm;
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
    public String detailArticle(@PathVariable("id") Long id, Model model, CommentForm commentForm) {
        Article article = this.articleService.getArticle(id);
        this.articleService.view(article, article.getViewCount());
        model.addAttribute("article", article);
        return "article_detail";
    }


    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/{id}/modify")
    public String modifyArticle(@PathVariable("id") Long id, ArticleForm articleForm, Principal principal, Model model) {
        Article article = this.articleService.getArticle(id);
        model.addAttribute("mbti", article.getMbti());
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
    public String deleteArticle(@PathVariable("id") Long id, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        Mbti mbti = member.getMbti();
        Article article = this.articleService.getArticle(id);
        if (!article.getAuthor().getMembername().equals(principal.getName()) &&
                !principal.getName().equals("admin")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.articleService.delete(article);
        return String.format("redirect:/mbti/%s", member.getMbti().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/article/{id}/like")
    public String likeOrUnlikeArticle(@PathVariable("id") Long id, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        Article article = this.articleService.getArticle(id);

        if (article != null && member != null) {
            if (article.getLikers().contains(member)) {
                this.articleService.unlike(article, member);
                article.setLikedByCurrentUser(false);
            } else {
                this.articleService.like(article, member);
                article.setLikedByCurrentUser(true);
            }
            this.articleService.saveArticle(article);
        }
        return String.format("redirect:/article/%s", id);
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/article/report/{id}")
    public String reportComment(@PathVariable("id") Long id, Principal principal,
                                @Valid ReportArticleForm reportArticleForm,BindingResult bindingResult)  {
        Article article = this.articleService.getArticle(id);

        if (bindingResult.hasErrors()){
            return "article_detail";
        }

        Member member = this.memberService.getMember(principal.getName());
        String reportType = determineReportType(reportArticleForm.getReportType());

        this.articleService.report(article,reportArticleForm.getReportContent(),member,reportType);
        return String.format("redirect:/article/%s", id);
    }

    private String determineReportType(String selectedValue) {

        if ("욕설".equals(selectedValue)) {
            return "욕설";
        } else if ("스팸 및 광고".equals(selectedValue)) {
            return "스팸 및 광고";
        } else if ("불법적인 콘텐츠".equals(selectedValue)) {
            return "불법적인 콘텐츠";
        }
        return "기타";
    }
}
