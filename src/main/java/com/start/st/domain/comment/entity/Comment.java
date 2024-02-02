package com.start.st.domain.comment.entity;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.reportComment.entity.ReportComment;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Comment extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne
    private Member author;

    @ManyToOne
    private Article article;

    @ManyToOne
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> childrenCommentList;

    @ManyToMany
    private Set<Member> likers = new HashSet<>();

    private boolean likedByCurrentUser;

    @OneToMany(mappedBy = "comment")
    private List<ReportComment> reportComments;



}
