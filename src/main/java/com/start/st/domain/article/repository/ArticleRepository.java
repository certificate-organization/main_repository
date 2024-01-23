package com.start.st.domain.article.repository;

import com.start.st.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    Article findByMbtiId(Long mbtiId);
}
