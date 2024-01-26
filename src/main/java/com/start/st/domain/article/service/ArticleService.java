package com.start.st.domain.article.service;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.repository.ArticleRepository;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }

    public Article getArticle(Long articleId) {
        Optional<Article> article = this.articleRepository.findById(articleId);
        if (article.isEmpty()) {
            return null;
        }
        return article.get();
    }

    public Page<Article> getArticlePage(int page) {
        Pageable pageable = PageRequest.of(page, 10);  //한 번에 볼 사이즈 수정
        return this.articleRepository.findAll(pageable);
    }

    public Page<Article> getArticlePageByMbti(String keyword, Long mbtiId, int page) {
        List<Sort.Order> list = new ArrayList<>();
        list.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(list));  //한 번에 볼 사이즈 수정
        return this.articleRepository.findAllByKeywordAndMbtiId(keyword, mbtiId, pageable);
    }

    public List<Article> getArticleByMbti(Long mbtiId) {
        return this.articleRepository.findByMbtiId(mbtiId);
    }

    public void create(String subject, String content, Mbti mbti, Member author) {
        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .mbti(mbti)
                .author(author)
                .viewCount(0L)
                .build();
        this.articleRepository.save(article);
    }

    public void modify(Article article, String subject, String content) {
        Article modifyArticle = article.toBuilder()
                .subject(subject)
                .content(content)
                .build();

        this.articleRepository.save(modifyArticle);
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    public void like(Article article, Member member) {
        article.getLikers().add(member);
        this.articleRepository.save(article);
    }

    public void view(Article article, Long viewCount) {
        Article viewArticle = article.toBuilder()
                .viewCount(viewCount + 1L)
                .build();
        this.articleRepository.save(viewArticle);
    }

}
