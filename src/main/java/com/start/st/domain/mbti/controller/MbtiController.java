package com.start.st.domain.mbti.controller;


import com.start.st.domain.mbti.service.MbtiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class MbtiController {
    private final MbtiService mbtiService;
}
