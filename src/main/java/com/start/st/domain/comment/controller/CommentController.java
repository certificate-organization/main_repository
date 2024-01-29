package com.start.st.domain.comment.controller;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.comment.form.CommentForm;
import com.start.st.domain.comment.service.CommentService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

import org.springframework.security.access.prepost.PreAuthorize;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;
    private final MemberService memberService;

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

        if (commentForm.getParentCommentId() == null) {
            // Regular comment
            this.commentService.create(article, commentForm.getContent(), member);
        } else {
            // Reply to a comment
            Comment parentComment = this.commentService.getcomment(commentForm.getParentCommentId());

            // Calculate reStep and reLevel for the new reply
            int reStep = parentComment.getReStep() + 1;
            int reLevel = parentComment.getReLevel() + 1;

            this.commentService.createReply(parentComment, commentForm.getContent(), member);
        }
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
        if (!comment.getAuthor().getMembername().equals(principal.getName())) {
            return "article_detail";
        }
        this.commentService.delete(comment);

        return String.format("redirect:/article/%s", comment.getArticle().getId());

    }


}
