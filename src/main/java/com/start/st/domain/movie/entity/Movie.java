package com.start.st.domain.movie.entity;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.global.jpa.BaseEntity;
import com.start.st.global.jpa.StringListConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Movie extends BaseEntity {
    private String genre;
    @Convert(converter = StringListConverter.class)
    private List<String> names;
}
