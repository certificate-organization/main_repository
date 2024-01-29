package com.start.st.domain.comment.form;


import com.start.st.domain.comment.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {

    @NotEmpty(message = "내용은 필수입력 사항입니다.")
    private String content;

    private Long parentCommentId;

    public Long getParentCommentId() {
        return parentCommentId;
    }

    public void setParentComment(Comment parentComment) {

    }

    public Comment getParentComment() {
        return null;
    }
}