package com.start.st.domain.mbtiInformation.entity;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class MbtiInfo extends BaseEntity {
    @Column(unique = true, columnDefinition = "TEXT")
    private String content;
    @OneToOne(mappedBy = "mbtiInfo")
    private Mbti mbti;
}
