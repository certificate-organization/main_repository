package com.start.st;

import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.service.MbtiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    ArticleService articleService;
    MbtiService mbtiService;

    @Test
    void contextLoads() {
        articleService.create("제목입니다", "내용입니다");
    }

    @Test
    void registerMbti() {
        mbtiService.create("ISTJ");
        mbtiService.create("ISTP");
        mbtiService.create("ISFJ");
        mbtiService.create("ISFP");
        mbtiService.create("INTJ");
        mbtiService.create("INTP");
        mbtiService.create("INFJ");
        mbtiService.create("INFP");
        mbtiService.create("ESTJ");
        mbtiService.create("ESTP");
        mbtiService.create("ESFJ");
        mbtiService.create("ESFP");
        mbtiService.create("ENTJ");
        mbtiService.create("ENTP");
        mbtiService.create("ENFJ");
        mbtiService.create("ENFP");
    }
}
