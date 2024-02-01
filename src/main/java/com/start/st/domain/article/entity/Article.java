package com.start.st.domain.article.entity;

import com.start.st.domain.comment.entity.Comment;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.reportArticle.entity.ReportArticle;
import com.start.st.domain.reportComment.entity.ReportComment;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.mapping.ToOne;

import java.util.List;
import java.util.Set;


@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    private String subject;
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    private Member author;
    @ManyToOne
    private Mbti mbti;
    @ManyToMany
    private Set<Member> likers;
    private Long viewCount;
    @OneToMany(mappedBy = "article", cascade=CascadeType.REMOVE)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "article")
    private List<ReportArticle> reportComments;
}
