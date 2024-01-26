package com.start.st.domain.comment.service;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.comment.Repository.CommentRepository;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.member.entity.Member;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public void create(Article article, String content, Member author){
        Comment comment = Comment.builder()
                .article(article)
                .content(content)
                .author(author)
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
        Comment modifyComment = comment.toBuilder()
                .content(content)
                .build();
        this.commentRepository.save(modifyComment);
    }
    public  void  delete(Comment comment){
        this.commentRepository.delete(comment);
    }

    public Comment createReply(Comment parentComment, String content, Member author) {
        Comment reply = Comment.builder()
                .parentComment(parentComment)
                .content(content)
                .author(author)
                .build();

        return this.commentRepository.save(reply);
    }


}
