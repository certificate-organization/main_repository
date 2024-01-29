package com.start.st.domain.comment.service;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.comment.Repository.CommentRepository;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.comment.form.CommentForm;
import com.start.st.domain.member.entity.Member;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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

    @Transactional
    public Comment create(Article article, String content, Member author) {
        Comment comment = Comment.builder()
                .article(article)
                .content(content)
                .author(author)
                .ref(article.getId())
                .reStep(0L)
                .reLevel(0L)
                .build();

        if (comment.getParentComment() == null) {
            Long maxRef = commentRepository.findMaxRef();
            comment.setRef(maxRef + 1);
        }

        return this.commentRepository.save(comment);
    }

    @Transactional
    public Comment createReply(CommentForm replyForm, Member author) {
        Comment parentComment = replyForm.getParentComment();

        Comment reply = Comment.builder()
                .content(replyForm.getContent())
                .author(author)
                .article(parentComment.getArticle())
                .ref(parentComment.getRef())
                .reStep(parentComment.getReStep() + 1)
                .reLevel(parentComment.getReLevel() + 1)
                .parentComment(parentComment)
                .build();

        parentComment.getReplies().add(reply);

        return commentRepository.save(reply);

    }
}
