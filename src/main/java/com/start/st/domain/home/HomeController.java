package com.start.st.domain.home;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MbtiService mbtiService;
    private final ArticleService articleService;
    private final MemberService memberService;

    @GetMapping("/")
    public String root(Model model, Principal principal ,@RequestParam(value = "page", defaultValue = "0") int page) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        model.addAttribute("mbtiList", mbtiList);
        if (principal != null) {
            String username = principal.getName();
            Member member = memberService.getMember(principal.getName());
            Page<Article> articleList = this.articleService.getArticlePageByDate(page);
            model.addAttribute("articleList",articleList);
            model.addAttribute("nickname", member.getNickname());
            return "mbti_home";
        } else {
            return "mbti_home";
        }

    }



    @GetMapping("/test")
    public String test() {

        return "test";
    }
}
