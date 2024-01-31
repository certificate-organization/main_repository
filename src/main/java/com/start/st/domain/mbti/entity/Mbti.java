package com.start.st.domain.mbti.entity;

import ch.qos.logback.core.testUtil.StringListAppender;
import com.start.st.domain.article.entity.Article;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.music.entity.Music;
import com.start.st.global.jpa.BaseEntity;
import com.start.st.global.jpa.StringListConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Mbti extends BaseEntity {
    @Column(length = 100, unique = true)
    private String name;
    @ManyToMany
    private Set<Movie> movieList;
    @ManyToMany
    private Set<Music> musicList;
    @Convert(converter = StringListConverter.class)
    private List<String> elements;
    private String love;
    private String relationship;
    private String celebrity;
    private String job;
}
