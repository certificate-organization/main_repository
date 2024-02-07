package com.start.st.domain.member.controller;

import com.start.st.domain.email.service.EmailService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.entity.MemberModifyForm;
import com.start.st.domain.member.entity.MemberSignupForm;
import com.start.st.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final MbtiService mbtiService;
    private final EmailService emailService;

    @GetMapping("/signup")
    public String memberSignup(Model model, MemberSignupForm memberSignupForm) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        model.addAttribute("mbtiList", mbtiList);
        return "signup_form";
    }

    @PostMapping("/signup")
    public String memberSignup(@Valid MemberSignupForm memberSignupForm,
                               BindingResult bindingResult, Model model,
                               @RequestParam("memberImg") MultipartFile memberImg) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        if (this.memberService.getMember(memberSignupForm.getMembername()) != null) {
            bindingResult.rejectValue("membername", "duplicateMembernameError",
                    "중복된 아이디가 존재합니다.");
        }
        if (this.memberService.findByNickname(memberSignupForm.getNickname()) != null) {
            bindingResult.rejectValue("nickname", "duplicateNicknameError",
                    "중복된 닉네임이 존재합니다.");
        }
        if (this.memberService.findByEmail(memberSignupForm.getEmail()) != null) {
            bindingResult.rejectValue("email", "duplicateEmailError",
                    "중복된 이메일이 존재합니다.");
        }
        if (!memberSignupForm.getPassword1().equals(memberSignupForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrectError",
                    "비밀번호와 비밀번호 재확인이 일치하지 않습니다.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("mbtiList", mbtiList);
            return "signup_form";
        }
        Mbti mbti = this.mbtiService.getMbti(memberSignupForm.getMbtiId());
        this.memberService.create(memberSignupForm.getMembername(), memberSignupForm.getPassword1(),
                memberSignupForm.getNickname(), memberSignupForm.getEmail(), mbti, memberImg);
        emailService.send(memberSignupForm.getEmail(), "서비스 가입을 환영합니다!", "회원가입 환영 메일");
        return "redirect:/member/login";
    }

    @GetMapping("/login")
    public String memberLogin() {
        return "login_form";
    }

    @GetMapping("/modify")
    public String memberModify(Model model, Principal principal, MemberModifyForm memberModifyForm) {
        Member member = this.memberService.getMember(principal.getName());
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        mbtiList.remove(member.getMbti());
        model.addAttribute("member", member);
        model.addAttribute("mbtiList", mbtiList);
        return "member_modify_form";
    }

    @PostMapping("/modify")
    public String memberModify(@Valid MemberModifyForm memberModifyForm, BindingResult bindingResult,
                               Model model, Principal principal,
                               @RequestParam("memberImg") MultipartFile memberImg) {
        Member member = this.memberService.getMember(principal.getName());
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        mbtiList.remove(member.getMbti());
        if (!this.memberService.paswordConfirm(memberModifyForm.getPassword(), member)) {
            bindingResult.rejectValue("password", "passwordInCorrectError",
                    "정보를 수정하려면 올바른 비밀번호를 입력하세요.");
        }
        if (this.memberService.findByNickname(memberModifyForm.getNickname()) != null) {
            if (!memberModifyForm.getNickname().equals(member.getNickname())) {
                bindingResult.rejectValue("nickname", "duplicateNicknameError",
                        "중복된 닉네임이 존재합니다.");
            }
        }
        if (this.memberService.findByEmail(memberModifyForm.getEmail()) != null) {
            if (!memberModifyForm.getEmail().equals(member.getEmail())) {
                bindingResult.rejectValue("email", "duplicateEmailError",
                        "중복된 이메일이 존재합니다.");
            }
        }
        if (memberModifyForm.getPassword1() != null && !memberModifyForm.getPassword1().isEmpty()) {
            if (!memberModifyForm.getPassword1().equals(memberModifyForm.getPassword2())) {
                bindingResult.rejectValue("password2", "passwordInCorrectError",
                        "변경할 비밀번호와 변경할 비밀번호 재확인이 일치하지 않습니다.");
            }
            if (memberModifyForm.getPassword1().length() < 4 || memberModifyForm.getPassword1().length() > 50)
                bindingResult.rejectValue("password1", "passwordLengthError",
                        "비밀번호는 4자 이상 50이하의 길이만 가능합니다.");
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("member", member);
            model.addAttribute("mbtiList", mbtiList);
            return "member_modify_form";
        }
        Mbti mbti = this.mbtiService.getMbti(memberModifyForm.getMbtiId());
        this.memberService.modify(memberModifyForm.getMembername(), memberModifyForm.getNickname(),
                memberModifyForm.getEmail(), mbti, memberModifyForm.getPassword1(), memberImg);
        return "redirect:/member/modify";
    }
}
