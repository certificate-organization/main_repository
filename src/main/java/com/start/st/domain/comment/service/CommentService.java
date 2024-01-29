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

    public Comment create(Article article, String content, Member author) {
        Comment comment = Comment.builder()
                .article(article)
                .content(content)
                .author(author)
                .build();

        // Set the article field to null for replies
        comment.setArticle(null);

        return this.commentRepository.save(comment);
    }

    public Comment createReply(Comment parentComment, String content, Member author) {
        int parentStep = parentComment.getReStep();
        int parentLevel = parentComment.getReLevel();

        System.out.println("Parent Step: " + parentStep);
        System.out.println("Parent Level: " + parentLevel);

        Comment reply = Comment.builder()
                .parentComment(parentComment)
                .content(content)
                .author(author)
                .reStep(parentStep + 1)  // 부모 댓글의 RE_STEP 값에 +1을 한 값을 사용
                .reLevel(parentLevel + 1)  // 부모 댓글의 RE_LEVEL 값에 +1을 한 값을 사용
                .build();

        System.out.println("Reply Step: " + reply.getReStep());
        System.out.println("Reply Level: " + reply.getReLevel());

        // Set the article field to null for replies
        reply.setArticle(null);

        // Save the reply
        reply = this.commentRepository.save(reply);

        // Update the parent comment's replies set
        parentComment.getReplies().add(reply);
        this.commentRepository.save(parentComment);

        return reply;
    }

}
