package com.start.st.domain.article.repository;

import com.start.st.domain.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByMbtiId(Long mbtiId);

}
