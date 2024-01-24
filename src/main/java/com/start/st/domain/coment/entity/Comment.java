package com.start.st.domain.coment.entity;

import com.start.st.domain.member.entity.Member;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.*;
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
public class Comment extends BaseEntity {
    @Column(columnDefinition = "TEXT")
    private String content;
    @ManyToOne
    private Member author;
    @ManyToMany
    private Set<Member> likers;
}
