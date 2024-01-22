package com.start.st.domain.article.service;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;

    public void create(String subject, String content){
        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .build();
        this.articleRepository.save(article);
    }

    public List<Article> findAll() {
        return this.articleRepository.findAll();
    }
}
