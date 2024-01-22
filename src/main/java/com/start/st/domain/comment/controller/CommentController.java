package com.start.st.domain.comment.controller;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.comment.form.CommentForm;
import com.start.st.domain.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;

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

}
