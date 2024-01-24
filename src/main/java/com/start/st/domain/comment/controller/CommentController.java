package com.start.st.domain.comment.controller;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.comment.form.CommentForm;
import com.start.st.domain.comment.service.CommentService;
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

    @PostMapping("/create/{id}")
    public String commentCreate(Model model,@PathVariable(value = "id")Long id,
                                @Valid CommentForm commentForm, BindingResult bindingResult){
        Article article = this.articleService.getArticle(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("article",article);
            return "article_detail";

        }
        this.commentService.create(article, commentForm.getContent());
        return String.format("redirect:/article/detail/%s",id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String commentModify(@PathVariable("id")Long id, Principal principal){

        Comment comment = this.commentService.getcomment(id);
        if (!comment.getAuthor().getMembername().equals(principal.getName())){
                return "article_detail";
        }
        return "comment_modify_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String commentModify(@PathVariable(value = "id")Long id,
                                @Valid CommentForm commentForm){
        Comment comment = this.commentService.getcomment(id);
        this.commentService.modify(comment,commentForm.getContent());
        return "redirect:/article/detail/"+id;
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String commentDelete(@PathVariable("id")Long id,Principal principal){
        Comment comment = this.commentService.getcomment(id);
        if (!comment.getAuthor().getMembername().equals(principal.getName())) {
            return "article_detail";
        }
        this.commentService.delete(comment);

        return "article_detail";

    }



}
