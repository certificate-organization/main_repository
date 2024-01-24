package com.start.st.domain.article.service;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.repository.ArticleRepository;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void create(String subject, String content, Mbti mbti, Member author){
        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .mbti(mbti)
                .author(author)
                .build();
        this.articleRepository.save(article);
    }
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
}
