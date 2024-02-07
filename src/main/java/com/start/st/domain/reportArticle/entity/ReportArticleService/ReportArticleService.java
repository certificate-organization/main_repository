package com.start.st.domain.reportArticle.entity.ReportArticleService;

import com.start.st.domain.reportArticle.entity.ReportArticle;
import com.start.st.domain.reportArticle.entity.ReportArticleListRepository.ReportArticleListRepository;
import com.start.st.domain.reportComment.ReportComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportArticleService {
    private final ReportArticleListRepository reportArticleListRepository;

    public List<ReportArticle> getList(){
        return this.reportArticleListRepository.findAll();
    }
}
