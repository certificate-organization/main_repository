package com.start.st.domain.member.controller;

import com.start.st.domain.member.entity.MemberSignupForm;
import com.start.st.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/signup")
    public String memberSignup() {
        return "signup_form";
    }

    @PostMapping("/member/signup")
    public String memberSignup(@Valid MemberSignupForm memberSignupForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "signup_form";
        }
        if(!memberSignupForm.getPassword1().equals(memberSignupForm.getPassword2())){
            bindingResult.rejectValue("password2","passwordInCorrect",
                    "비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
            return "signup_form";
        }
        this.memberService.create(memberSignupForm.getMembername(), memberSignupForm.getPassword1(),
                memberSignupForm.getNickname(), memberSignupForm.getEmail(), memberSignupForm.getMbti());
        return "redirect:/member/login";
    }
    @GetMapping("/member/login")
    public String memberLogin(){
        return "login_form";
    }
}
