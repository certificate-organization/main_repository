package com.start.st.domain.comment.controller;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.comment.form.ReplyForm;
import com.start.st.domain.comment.form.CommentForm;
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
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private ArticleService articleService;

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
        this.commentService.create(article, commentForm.getContent(), member);
        return String.format("redirect:/article/%s", id);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/reply/{commentId}")
    public String createCommentReply(@PathVariable("commentId") Long commentId,
                                     @Valid ReplyForm replyForm, BindingResult bindingResult, Principal principal) {
        Comment parentComment = this.commentService.getcomment(commentId);
        Member member = this.memberService.getMember(principal.getName());

        if (parentComment != null && member != null) {
            if (bindingResult.hasErrors()) {
                return "article_detail";
            }

            CommentForm commentForm = new CommentForm();
            commentForm.setContent(replyForm.getReplyContent());
            commentForm.setParentComment(parentComment);

            Comment reply = this.commentService.createReply(commentForm, member);

            return String.format("redirect:/article/%s", reply.getArticle().getId());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Parent comment or user not found");
        }
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
