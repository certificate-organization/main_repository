package com.start.st.domain.comment.controller;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final ArticleService articleService;

    @PostMapping("/create/{id}")
    public String comment(@PathVariable(value = "id")Long id, @RequestParam(value = "content")String content){
        Article article = this.articleService.getArticle(id);
        this.commentService.create(article, content);
        return String.format("redirect:/article/detail/%s",id);
    }

}
