package com.start.st.domain.comment.service;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.comment.Repository.CommentRepository;
import com.start.st.domain.comment.entity.Comment;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Article article,String content){
        Comment comment = Comment.builder()
                .article(article)
                .content(content)
                .build();

        this.commentRepository.save(comment);
    }
    public Comment getcomment(Long id){
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isEmpty()){
            return null;
        }
        return comment.get();
    }
    public void modify(Comment comment,String content){
        Comment modifyComment = Comment.builder()
                .content(content)
                .build();
        this.commentRepository.save(modifyComment);
    }
    public  void  delete(Comment comment){
        this.commentRepository.delete(comment);
    }


}
