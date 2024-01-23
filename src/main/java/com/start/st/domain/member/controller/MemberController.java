package com.start.st.domain.member.controller;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.MemberSignupForm;
import com.start.st.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final MbtiService mbtiService;

    @GetMapping("/member/signup")
    public String memberSignup(Model model, MemberSignupForm memberSignupForm) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        model.addAttribute("mbtiList", mbtiList);
        return "signup_form";
    }

    @PostMapping("/member/signup")
    public String memberSignup(@Valid MemberSignupForm memberSignupForm, BindingResult bindingResult, @RequestParam(value = "mbti") String id, Model model) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        if (bindingResult.hasErrors()) {
            model.addAttribute("mbtiList", mbtiList);
            return "signup_form";
        }
        if (!memberSignupForm.getPassword1().equals(memberSignupForm.getPassword2())) {
            model.addAttribute("mbtiList", mbtiList);
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
            return "signup_form";
        }
        Mbti mbti = this.mbtiService.getMbti(Long.valueOf(id));
        this.memberService.create(memberSignupForm.getMembername(), memberSignupForm.getPassword1(),
                memberSignupForm.getNickname(), memberSignupForm.getEmail(), mbti);
        return "redirect:/member/login";
    }

    @GetMapping("/member/login")
    public String memberLogin() {
        return "login_form";
    }
}
