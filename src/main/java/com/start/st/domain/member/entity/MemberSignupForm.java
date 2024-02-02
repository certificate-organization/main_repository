package com.start.st.domain.member.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberSignupForm {

    @NotEmpty(message = "올바르지 않은 이름 형식 입니다.")
    @Size(min = 3, max = 50)
    private String membername;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String password1;

    @NotEmpty(message = "비밀번호 재확인을 입력해주세요.")
    private String password2;

    @NotEmpty(message = "글자수 초과 또는 올바르지 않은 닉네임입니다.")
    @Size(min = 3, max = 50)
    private String nickname;

    @NotEmpty(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 형식이 아닙니다.")
    private String email;

    @Min(value = 1, message = "MBTI를 선택해주세요.")
    @Max(value = 16, message = "MBTI를 선택해주세요.")
    private Long mbtiId;
}
