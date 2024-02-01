package com.start.st.domain.comment.form;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportCommentForm {
    @NotEmpty(message = "신고 내용은 필수입력 사항입니다.")
    private String reportContent;
}
