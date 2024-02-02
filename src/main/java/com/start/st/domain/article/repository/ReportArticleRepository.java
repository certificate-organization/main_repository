package com.start.st.domain.article.repository;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.reportArticle.entity.ReportArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportArticleRepository extends JpaRepository<ReportArticle, Long> {
}
