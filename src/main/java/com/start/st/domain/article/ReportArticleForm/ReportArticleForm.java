package com.start.st.domain.article.ReportArticleForm;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportArticleForm {
    @NotEmpty(message = "신고 내용은 필수입력 사항입니다.")
    private String reportContent;

    private String badSpeak;
    private String badAdvertise;
    private String badContent;

    private String selectedValue;

    private String subject;

    private String radioButtonValue;

    private String reportType;
}
