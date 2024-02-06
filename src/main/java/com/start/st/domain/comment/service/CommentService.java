package com.start.st.domain.comment.service;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.comment.Repository.CommentRepository;
import com.start.st.domain.comment.Repository.ReportCommentRepository;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.reportComment.entity.ReportComment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ReportCommentRepository reportCommentRepository;

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
    public void likeComment(Comment comment, Member liker){
        comment.getLikers().add(liker);
        this.commentRepository.save(comment);
    }

    public void unlikeComment(Comment comment, Member unLiker){
        comment.getLikers().remove(unLiker);
        this.commentRepository.save(comment);
    }
    public void report(Comment comment, String reportContent, Member member, String reportType) {

        ReportComment reportComment = ReportComment.builder()
                .author(member)
                .comment(comment)
                .content(reportContent)
                .reportType(reportType)
                .build();


        this.reportCommentRepository.save(reportComment);
    }
}
