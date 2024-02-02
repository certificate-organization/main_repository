package com.start.st;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.service.CommentService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.service.MemberService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

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
        memberService.create("admin", "1234", "dsds",
                "dsdsd@fsd.com", mbti);

    }

    @Test
    void makeMember() {
        Mbti mbti = mbtiService.getMbti(1L);
        for (int i = 1; i < 10; i++) {
            memberService.create("" + i + i + i, "" + i + i + i, "" + i + i + i, "" + i + i + i + "@email.com", mbti);
        }
    }

    @Test
    void makeArticle() {
        Mbti mbti = mbtiService.getMbti(1L);
        Random random = new Random();
        // 11부터 19까지의 랜덤한 숫자 생성
        for (int i = 1; i < 200; i++) {
            Long randomNumber = (long) (random.nextDouble() * (19 - 11 + 1)) + 11;
            Member member = memberService.findByMemberId((randomNumber));
            articleService.create(i + "번 게시글 제목", i + "번 게시글 내용 입니다.", mbti, member);
        }
    }

    @Test
    void makeViewCount() {
        Random random = new Random();
        for (int i = 207; i < 406; i++) {
            Long randomNumber = (long) (random.nextDouble() * 5001);
            Long id = (long) i;
            Article article = articleService.getArticle(id);
            articleService.view(article,randomNumber);
        }
    }

    @Autowired
    CommentService commentService;
    @Test
    void makeComment(){
        for(int i = 207;i<406;i++){
            Long id = (long) i;
            Article article = articleService.getArticle(id);
            Member member = memberService.getMember("admin");
            commentService.create(article,"1번 댓글 입니다.",member,null);
            commentService.create(article,"2번 댓글 입니다.",member,null);
        }
    }
}
