package com.start.st.domain.member.entity;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberModifyForm {

    @NotEmpty(message = "올바르지 않은 이름 형식 입니다.")
    @Size(min = 3, max = 50)
    private String membername;

    @NotEmpty(message = "글자수 초과 또는 올바르지 않은 닉네임입니다.")
    @Size(min = 2, max = 50,message = "닉네임 길이는 2자이상 50자 미만 입니다.")
    private String nickname;

    @NotEmpty(message = "올바르지 않은 이메일 입니다.")
    @Email
    private String email;

    @Min(value = 1, message = "MBTI를 선택해주세요.")
    @Max(value = 16, message = "MBTI를 선택해주세요.")
    private Long mbtiId;

    private String password;

    @Size(min = 4, max = 50,message = "비밀번호 길이는 4자이상 50자 미만 입니다.")
    private String password1;

    private String password2;
}
