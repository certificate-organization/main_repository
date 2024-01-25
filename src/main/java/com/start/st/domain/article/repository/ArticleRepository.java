package com.start.st.domain.article.repository;

import com.start.st.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByMbtiId(Long mbtiId);

    Page<Article> findByMbtiId(Long mbtiId, Pageable pageable);
    Page<Article> findAll(Pageable pageable);

}
