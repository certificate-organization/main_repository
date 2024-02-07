package com.start.st.domain.reportComment;

import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.member.entity.Member;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ReportComment extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    private Member author;
    @ManyToOne
    private Comment comment;
    private String subject;
    private String content;
    private String radioButtonValue;
    private String reportType;

    @CreatedDate
   private LocalDateTime createDate;
//    @LastModifiedDate
//    private LocalDateTime modifyDate;
}
