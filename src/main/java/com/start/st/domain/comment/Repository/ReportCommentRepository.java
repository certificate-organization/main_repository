package com.start.st.domain.comment.Repository;

import com.start.st.domain.reportComment.entity.ReportComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCommentRepository extends JpaRepository<ReportComment,Long> {
}
