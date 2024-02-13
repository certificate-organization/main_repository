package com.start.st.domain.reportComment.ReportCommentService;

import com.start.st.domain.reportComment.ReportCommentListRepository.ReportCommentListRepository;
import com.start.st.domain.reportComment.ReportComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportCommentService {
    private final ReportCommentListRepository reportCommentListRepository;

    public List<ReportComment> getList(){
        return this.reportCommentListRepository.findAll();
    }
}
