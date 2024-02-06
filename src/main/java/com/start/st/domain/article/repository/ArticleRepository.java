package com.start.st.domain.article.repository;

import com.start.st.domain.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByMbtiId(Long mbtiId);

    Page<Article> findByMbtiId(Long mbtiId, Pageable pageable);

    Page<Article> findAll(Pageable pageable);

    @Query("select "
            + "distinct A "
            + "from Article A "
            + "left outer join Member M on A.author=M "
            + "where "
            + "   (A.subject like %:keyword% "
            + "   or A.content like %:keyword% "
            + "   or M.membername like %:keyword%) "
            + "   and A.mbti.id = :mbtiId")
    Page<Article> findAllByKeywordAndMbtiId(@Param("keyword") String keyword,
                                   @Param("mbtiId") Long mbtiId, Pageable pageable);

    @Query("SELECT a FROM Article a " +
            "LEFT JOIN a.likers l " +
            "GROUP BY a ORDER BY a.createDate DESC, COUNT(l) DESC")
    Page<Article> findAllOrderedByCreateDateAndLikersSizeDesc(Pageable pageable);
}
