package com.start.st;

import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    ArticleService articleService;



    @Autowired
    MbtiService mbtiService;

    @Test
    void registerMbti() { //mbti등록 ID
        mbtiService.create("ISTJ"); //1
        mbtiService.create("ISTP"); //2
        mbtiService.create("ISFJ"); //3
        mbtiService.create("ISFP"); //4
        mbtiService.create("INTJ"); //5
        mbtiService.create("INTP"); //6
        mbtiService.create("INFJ"); //7
        mbtiService.create("INFP"); //8
        mbtiService.create("ESTJ"); //9
        mbtiService.create("ESTP"); //10
        mbtiService.create("ESFJ"); //11
        mbtiService.create("ESFP"); //12
        mbtiService.create("ENTJ"); //13
        mbtiService.create("ENTP"); //14
        mbtiService.create("ENFJ"); //15
        mbtiService.create("ENFP"); //16
    }

    @Autowired
    MemberService memberService;
    @Test
    void registerMember() {
        Mbti mbti = mbtiService.getMbti(1L);
        memberService.create("admin","1234","dsds",
                "dsdsd@fsd.com",mbti);

    }

}
