package com.start.st.domain.mbti.controller;


import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class MbtiController {
    private final MbtiService mbtiService;

    @GetMapping("/mbti/{id}")
    public String mbtiDetail(@PathVariable("id") Long id, Model model) {
        Mbti mbti = this.mbtiService.getMbti(id);
        model.addAttribute("mbti", mbti);
        return String.format("mbti_detail");
    }


}
