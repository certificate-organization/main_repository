package com.start.st.domain.article.entity;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {
    @NotEmpty(message = "제목을 입력해주세요.")
    private String subject;
    @NotEmpty(message = "내용을 입력해주세요.")
    private String content;
}
