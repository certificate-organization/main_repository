package com.start.st.domain.mbti.entity;

import com.start.st.domain.mbtiInformation.entity.MbtiInfo;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Mbti extends BaseEntity {
    @Column(length = 100, unique = true)
    private String name;
    @OneToOne
    @JoinColumn(name = "info_id")
    private MbtiInfo mbtiInfo;
}
