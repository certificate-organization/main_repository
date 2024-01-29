package com.start.st.domain.comment.form;

import jakarta.validation.constraints.NotBlank;

public class ReplyForm {

    @NotBlank(message = "Reply content cannot be empty")
    private String replyContent;

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }
}
