package com.start.st.domain.article.entity;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.member.entity.Member;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Article extends BaseEntity {
    private String subject;
    private String content;
    @ManyToOne
    private Member author;
    @OneToOne
    private Mbti mbti;
    @OneToMany
    private Set<Member> likers;
}
