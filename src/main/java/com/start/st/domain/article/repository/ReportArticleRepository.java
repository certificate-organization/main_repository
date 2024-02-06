package com.start.st.domain.article.repository;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.reportArticle.entity.ReportArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReportArticleRepository extends JpaRepository<ReportArticle, Long> {


}
