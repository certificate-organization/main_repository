package com.start.st.domain.reportArticle.entity.ReportArticleController;

import com.start.st.domain.reportArticle.entity.ReportArticle;
import com.start.st.domain.reportArticle.entity.ReportArticleService.ReportArticleService;
import com.start.st.domain.reportComment.ReportComment;
import com.start.st.domain.reportComment.ReportCommentService.ReportCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReportArticleController {

    private final ReportArticleService reportArticleService;
    private final ReportCommentService reportCommentService;

    @GetMapping("/article/list")
    public String reportList(Model model){

        List<ReportArticle> reportArticleList= this.reportArticleService.getList();
        List<ReportComment> reportCommentList= this.reportCommentService.getList(); // 신고된 댓글 목록 가져오기

        model.addAttribute("reportArticleList", reportArticleList);
        model.addAttribute("reportCommentList", reportCommentList); // 모델에 추가

        return "report_list";
    }
}
