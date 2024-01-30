package com.start.st.domain.comment.service;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.comment.Repository.CommentRepository;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment getcomment(Long id) {
        Optional<Comment> comment = this.commentRepository.findById(id);
        if (comment.isEmpty()) {
            return null;
        }
        return comment.get();
    }

    public void modify(Comment comment, String content) {
        Comment modifyComment = comment.toBuilder()
                .content(content)
                .build();
        this.commentRepository.save(modifyComment);
    }

    public void delete(Comment comment) {
        this.commentRepository.delete(comment);
    }

    public void create(Article article, String content, Member author, Comment parent){
        Comment comment = Comment.builder()
                .article(article)
                .content(content)
                .author(author)
                .parent(parent)
                .build();

        this.commentRepository.save(comment);
    }
}
