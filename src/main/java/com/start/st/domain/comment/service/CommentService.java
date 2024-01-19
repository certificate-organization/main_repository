package com.start.st.domain.comment.service;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.comment.Repository.CommentRepository;
import com.start.st.domain.comment.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Article article,String subject,String content){
        Comment comment = Comment.builder()
                .subject(subject)
                .content(content)
                .article(article)
                .createDate(LocalDateTime.now())
                .build();

        this.commentRepository.save(comment);
    }

}
