package com.start.st.domain.comment.controller;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.entity.Comment;

import com.start.st.domain.comment.form.CommentForm;

import com.start.st.domain.comment.form.ReportCommentForm;
import com.start.st.domain.comment.service.CommentService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;
    private final MemberService memberService;
    private final ArticleService articleService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String commentCreate(Model model, @PathVariable(value = "id") Long id,
                                @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {
        Article article = this.articleService.getArticle(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("article", article);
            return "article_detail";
        }

        Member member = this.memberService.getMember(principal.getName());

        Comment parent = null;
        if (commentForm.getParentCommentId() != null) {
            parent = this.commentService.getcomment(commentForm.getParentCommentId());
        }

        this.commentService.create(article, commentForm.getContent(), member, parent);
        return String.format("redirect:/article/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String commentModify(@PathVariable("id") Long id, Principal principal, Model model) {

        Comment comment = this.commentService.getcomment(id);
        if (!comment.getAuthor().getMembername().equals(principal.getName())) {
            return "article_detail";
        }
        model.addAttribute("commentForm", new CommentForm());
        return "comment_modify_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String commentModify(@PathVariable(value = "id") Long id,
                                @Valid CommentForm commentForm, BindingResult bindingResult, Principal principal) {

        Comment comment = this.commentService.getcomment(id);
        if (!comment.getAuthor().getMembername().equals(principal.getName())) {
            return "comment_modify_form";
        }

        if (bindingResult.hasErrors()) {

            return "comment_modify_form";
        }
        this.commentService.modify(comment, commentForm.getContent());
        return String.format("redirect:/article/%s", comment.getArticle().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(@PathVariable("id") Long id, Principal principal) {
        Comment comment = this.commentService.getcomment(id);
        if (!comment.getAuthor().getMembername().equals(principal.getName()) &&
                !principal.getName().equals("admin")) {
            return "article_detail";
        }
        this.commentService.delete(comment);

        return String.format("redirect:/article/%s", comment.getArticle().getId());

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String likeComment(@PathVariable("id") Long id, Principal principal) {
        Comment comment = this.commentService.getcomment(id);
        Member liker = this.memberService.getMember(principal.getName());

        if (comment != null && liker != null) {
            this.commentService.likeComment(comment, liker);
        }
        return String.format("redirect:/article/%s", comment.getArticle().getId());
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/report/{id}")
    public String reportComment(@PathVariable("id") Long id, Principal principal,
                                @Valid ReportCommentForm reportCommentForm, BindingResult bindingResult)  {
        Comment comment = this.commentService.getcomment(id);

        if (bindingResult.hasErrors()) {
            return "article_detail";
        }
        Member member = this.memberService.getMember(principal.getName());

        String reportType = determineReportType(reportCommentForm.getReportType());

        this.commentService.report(comment, reportCommentForm.getReportContent(), member,reportType);

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
