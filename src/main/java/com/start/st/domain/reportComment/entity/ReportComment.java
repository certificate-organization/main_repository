package com.start.st.domain.reportComment.entity;

import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.member.entity.Member;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReportComment extends BaseEntity {
    @ManyToOne
    private Member author;
    @ManyToOne
    private Comment comment;
    private String subject;
    private String content;
}
