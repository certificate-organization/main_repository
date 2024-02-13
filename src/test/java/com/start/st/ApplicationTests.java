package com.start.st;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.repository.ArticleRepository;
import com.start.st.domain.article.service.ArticleService;
import com.start.st.domain.comment.service.CommentService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.service.MbtiService;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.repository.MemberRepository;
import com.start.st.domain.member.service.MemberService;
import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.movie.service.MovieService;
import com.start.st.domain.music.entity.Music;
import com.start.st.domain.music.service.MusicService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@SpringBootTest
class ApplicationTests {

    @Autowired
    ArticleService articleService;
    @Autowired
    ArticleRepository articleRepository;
    @Autowired
    MbtiService mbtiService;
    @Autowired
    MovieService movieService;
    @Autowired
    MusicService musicService;
    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    CommentService commentService;

    @Test
    void activeAll(){ //모든 데이터 한번에 생성
        registerMbti();
        registerMember();
        makeMember();
        makeArticle();
        makeViewCount();
        makeComment();
        registerMovieGenre();
        registerMusicGenre();
        registerMbtiInfo();
    }
    @Test
    void registerMbti() { // mbti 등록
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


    @Test
    void registerMember() { // 어드민 계정 생성
        Mbti mbti = mbtiService.getMbti(1L);
        Member member = Member.builder()
                .membername("admin")
                .password("1234")
                .nickname("관리자")
                .email("admin@email.com")
                .mbti(mbti)
                .memberImg(null)
                .build();
        memberRepository.save(member);
    }

    @Test
    void makeMember() { // 더미 계정 9개 생성  아이디 비번 각각 111, 222 , ... , 999 mbti는 ISTJ로 통일
        Mbti mbti = mbtiService.getMbti(1L);
        for (int i = 1; i < 10; i++) {
            Member member = Member.builder()
                    .membername("" + i + i + i)
                    .password("" + i + i + i)
                    .nickname("" + i + i + i)
                    .email("" + i + i + i + "@email.com")
                    .mbti(mbti)
                    .memberImg(null)
                    .build();
            memberRepository.save(member);
        }
    }

    @Test
    void makeArticle() { // 더미 계정으로 랜덤한 작성자를 갖는 게시글 199개 생성
        Mbti mbti = mbtiService.getMbti(1L);
        Random random = new Random();
        for (int i = 1; i < 200; i++) {
            Long randomNumber = (long) (random.nextDouble() * (10 - 2 + 1)) + 2;
            Member member = memberService.findByMemberId(randomNumber);
            Article article = Article.builder()
                    .subject(i + "번 게시글 제목")
                    .content(i + "번 게시글 내용 입니다.")
                    .mbti(mbti)
                    .author(member)
                    .articleImg(null)
                    .build();
            articleRepository.save(article);
        }
    }

    @Test
    void makeViewCount() { // 게시글 들에 5000까지 랜덤한 조회수 부여
        Random random = new Random();
        for (int i = 1; i < 200; i++) {
            Long randomNumber = (long) (random.nextDouble() * 5001);
            Long id = (long) i;
            Article article = articleService.getArticle(id);
            Article newArticle = article.toBuilder()
                    .viewCount(randomNumber)
                    .build();
            articleRepository.save(newArticle);
        }
    }


    @Test
    void makeComment() { // 게시글 마다 2개의 댓글 생성
        for (int i = 1; i < 200; i++) {
            Long id = (long) i;
            Article article = articleService.getArticle(id);
            Member member = memberService.getMember("admin");
            commentService.create(article, "1번 댓글 입니다.", member, null);
            commentService.create(article, "2번 댓글 입니다.", member, null);
        }
    }


    @Test
    void registerMovieGenre() { // 영화 장르와 장르에 맞는 영화 삽입
        List<String> names1 = new ArrayList<>();
        names1.add("매드 맥스");
        names1.add("존 윅");
        names1.add("다크 나이트");
        movieService.createTest("액션", names1);

        List<String> names2 = new ArrayList<>();
        names2.add("타짜");
        names2.add("히트");
        names2.add("범죄도시");
        movieService.createTest("범죄", names2);

        List<String> names3 = new ArrayList<>();
        names3.add("인터스텔라");
        names3.add("매트릭스");
        names3.add("인셉션");
        movieService.createTest("SF", names3);

        List<String> names4 = new ArrayList<>();
        names4.add("청년경찰");
        names4.add("극한 직업");
        movieService.createTest("코미디", names4);
    }

    @Test
    void registerMusicGenre() { // 음악 장르와 장르에 맞는 음악 삽입
        List<String> names1 = new ArrayList<>();
        names1.add("너에게로 또 다시 - 정승환");
        names1.add("밤편지 - IU");
        names1.add("소주 한 잔 - 임창정");
        musicService.createTest("발라드", names1);

        List<String> names2 = new ArrayList<>();
        names2.add("Black Swan - BTS");
        names2.add("아무노래 - ZICO");
        names2.add("HIP - Mamamoo");
        musicService.createTest("댄스", names2);

        List<String> names3 = new ArrayList<>();
        names3.add("Overdrive - Post Malone");
        names3.add("Players - Coi Leray");
        names3.add("Say So - Doja Cat");
        musicService.createTest("힙합", names3);

        List<String> names4 = new ArrayList<>();
        names4.add("Tiny Riot - Sam Ryder");
        names4.add("Demons - Imagine Dragons");
        names4.add("Bones - Imagine Dragons");
        musicService.createTest("락", names4);
    }

    @Test
    void registerMbtiInfo() { // 각 MBTI 정보 삽입
        Mbti mbti = mbtiService.getMbti(1L);
        Set<Movie> movieSet = new HashSet<>();
        Set<Music> musicSet = new HashSet<>();
        List<Long> ids = new ArrayList<>();
        ids.add(1L);
        ids.add(2L);
        movieSet = movieService.getMovieSet(ids);
        musicSet = musicService.getMusicSet(ids);
        mbtiService.modify(mbti,
                "##### **연애스타일\uD83D\uDE0D**\n" +
                        "1. 이성에게 잘 다가가서 연애\n" +
                        "2. 남들 잘 꼬시고 썸 잘타는 유형\n" +
                        "3. 자유분방하고 자존심 강함\n" +
                        "4. 서로의 사생활을 존중하는 연애를 원함\n" +
                        "5. 통제하고 억압하는 것 정말 싫어함\n" +
                        "6. 은근 특이한 사람에게 끌림\n" +
                        "7. 지적이라서 본인이 좋아하는 분야에 토론해 주는 사람 선호함",
                "##### **성격\uD83D\uDE36**\n" +
                        "조용하고 개인주의 성향이 강한 MBTI 유형입니다.\n" +
                        "호불호가 확실하고 자기 주관이 뚜렷하며 타인에게 대체로 관심이 적은 성격 입니다.",
                "##### **유명인\uD83D\uDE0E**\n" +
                        "1. 제시\n" +
                        "2. 한예슬\n" +
                        "3. 고민시\n" +
                        "4. YGX 리정\n" +
                        "5. 브레이브걸스 유정\n" +
                        "6. 블락비 박경\n" +
                        "7. 악동뮤지션 이찬혁\n" +
                        "8. 육성재\n" +
                        "9. 자이언티\n" +
                        "10. 로버트 다우니 주니어\n" +
                        "11. 조니 뎁\n" +
                        "12. 에밀리 블런트\n" +
                        "13. 크리스 프랫",
                "##### **직업\uD83E\uDDD0**\n" +
                        "파일럿, 카레이서, 범죄학자, 사진 작가, 판매원, 운동선수, 항공기 정비사, 네트워크 관리자",
                movieSet, musicSet
        );
    }
}