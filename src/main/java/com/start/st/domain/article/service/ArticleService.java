package com.start.st.domain.article.service;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.article.repository.ArticleRepository;
import com.start.st.domain.article.repository.ReportArticleRepository;
import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.reportArticle.entity.ReportArticle;
import com.start.st.domain.reportComment.entity.ReportComment;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ArticleService {
    private final ArticleRepository articleRepository;
    private final ReportArticleRepository reportArticleRepository;

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

    public Page<Article> getArticlePageByDate(int page) {
        List<Sort.Order> list = new ArrayList<>();
        list.add(Sort.Order.desc("createDate"));
        Pageable pageable = PageRequest.of(page, 11, Sort.by(list));  //한 번에 볼 사이즈 수정
        return this.articleRepository.findAll(pageable);
    }

    public Page<Article> getArticlePageByLike(int page) {
        Pageable pageable = PageRequest.of(page, 11);  //한 번에 볼 사이즈 수정
        return this.articleRepository.findAllOrderedByCreateDateAndLikersSizeDesc(pageable);
    }

    public Page<Article> getArticlePageByView(int page) {
        List<Sort.Order> list = new ArrayList<>();
        list.add(Sort.Order.desc("viewCount"));
        Pageable pageable = PageRequest.of(page, 11, Sort.by(list));  //한 번에 볼 사이즈 수정
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

    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public void create(String subject, String content, Mbti mbti, Member author,
                       MultipartFile articleImg) {
        String thumbnailRelPath = "";
        if (articleImg.isEmpty()) {
            thumbnailRelPath = null;
        } else {
            thumbnailRelPath = "article/" + UUID.randomUUID().toString() + ".jpg";
            File thumbnailFile = new File(fileDirPath + "/" + thumbnailRelPath);

            try {
                articleImg.transferTo(thumbnailFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Article article = Article.builder()
                .subject(subject)
                .content(content)
                .mbti(mbti)
                .author(author)
                .viewCount(0L)
                .articleImg(thumbnailRelPath)
                .build();
        this.articleRepository.save(article);
    }

    public void modify(Article article, String subject, String content,
                       MultipartFile articleImg) {
        String thumbnailRelPath = "";
        if (articleImg.isEmpty()) {
            thumbnailRelPath = null;
        } else {
            thumbnailRelPath = "article/" + UUID.randomUUID().toString() + ".jpg";
            File thumbnailFile = new File(fileDirPath + "/" + thumbnailRelPath);

            try {
                articleImg.transferTo(thumbnailFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Article modifyArticle = article.toBuilder()
                .subject(subject)
                .content(content)
                .articleImg(thumbnailRelPath)
                .build();

        this.articleRepository.save(modifyArticle);
    }

    public void delete(Article article) {
        this.articleRepository.delete(article);
    }

    public void like(Article article, Member member) {
        article.getLikers().add(member);
        article.setLikedByCurrentUser(true);
        this.articleRepository.save(article);
    }

    public void unlike(Article article, Member member) {
        article.getLikers().remove(member);
        article.setLikedByCurrentUser(false);
        this.articleRepository.save(article);
    }


    public void view(Article article, Long viewCount) {
        Article viewArticle = article.toBuilder()
                .viewCount(viewCount + 1L)
                .build();
        this.articleRepository.save(viewArticle);
    }

    public void report(Article article, String reportContent, Member member, String reportType) {

        ReportArticle reportArticle = ReportArticle.builder()
                .author(member)
                .article(article)
                .content(reportContent)
                .reportType(reportType)
                .build();

        this.reportArticleRepository.save(reportArticle);
    }

    public void saveArticle(Article article) {
    }
}
