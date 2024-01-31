package com.start.st.domain.member.controller;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.entity.MemberSignupForm;
import com.start.st.domain.member.security.MemberSecurityService;
import com.start.st.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MbtiService mbtiService;
    private final MemberSecurityService memberSecurityService;

    @GetMapping("/signup")
    public String memberSignup(Model model, MemberSignupForm memberSignupForm) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        model.addAttribute("mbtiList", mbtiList);
        return "signup_form";
    }

    @PostMapping("/signup")
    public String memberSignup(@Valid MemberSignupForm memberSignupForm, BindingResult bindingResult, Model model) {
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
        Mbti mbti = this.mbtiService.getMbti(memberSignupForm.getMbtiId());
        this.memberService.create(memberSignupForm.getMembername(), memberSignupForm.getPassword1(),
                memberSignupForm.getNickname(), memberSignupForm.getEmail(), mbti);
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String memberLogin() {
        return "login_form";
    }

    @GetMapping("/modify")
    public String memberModify(Model model, Principal principal, MemberSignupForm memberSignupForm, Authentication authentication){
        if (authentication == null || !authentication.isAuthenticated()) {
            // 인증되지 않은 사용자이거나, 인증되었지만 비밀번호 검증 상태가 아닌 경우 비밀번호 검증 페이지로 리디렉션
            return "redirect:/member/passwordConfirm";
        }
        Member member = this.memberService.getMember(principal.getName());
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        mbtiList.remove(member.getMbti());
        model.addAttribute("member",member);
        model.addAttribute("mbtiList",mbtiList);
        return "member_modify_form";
    }

    @GetMapping("/passwordConfirm")
    public String memberPasswordConfirm(){
        return "passwordConfirm_form";
    }
    @PostMapping("/passwordConfirm")
    public String memberPasswordConfirm(@RequestParam(value = "password")String password, Principal principal){
        Member member = this.memberService.getMember(principal.getName());
        if(!this.memberService.paswordConfirm(password,member)){
            return "/passwordConfirm";
        }

    }
}
