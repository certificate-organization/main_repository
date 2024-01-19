package com.start.st.domain.member.entity;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Column(length = 200, unique = true)
    private String membername;
    private String password;
    @Column(length = 200, unique = true)
    private String nickname;
    @Column(unique = true)
    private String email;
    @ManyToOne
    private Mbti mbti;
}

