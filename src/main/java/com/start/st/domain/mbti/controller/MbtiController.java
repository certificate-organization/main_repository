package com.start.st.domain.mbti.controller;


import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.entity.MemberSignupForm;
import com.start.st.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MbtiController {
    private final MbtiService mbtiService;
    private final MemberService memberService;
    private final ArticleService articleService;

    @GetMapping("/mbti/{id}")
    public String mbtiDetail(@PathVariable("id") Long id, Model model, Principal principal, @RequestParam(value = "page",defaultValue = "0") int page) {
        Page<Article> articlePage = this.articleService.getArticlePageByMbti(id, page);
        model.addAttribute("articlePage",articlePage);

        Mbti mbti = this.mbtiService.getMbti(id);
        model.addAttribute("mbti",mbti);
        if (principal != null) {
            Member member = this.memberService.getMember(principal.getName());
            model.addAttribute("member", member);
        }

        return String.format("mbti_detail");
    }


}
