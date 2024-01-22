package com.start.st.domain.home;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MbtiService mbtiService;

    @GetMapping("/")
    public String root(Model model) {
        List<Mbti> mbtiList = this.mbtiService.findAllMbti();
        model.addAttribute("mbtiList", mbtiList);
        return "mbti_home";
    }
}
