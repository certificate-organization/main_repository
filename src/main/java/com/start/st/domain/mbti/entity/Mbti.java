package com.start.st.domain.mbti.entity;

import com.start.st.domain.article.entity.Article;
import com.start.st.domain.mbtiInformation.entity.MbtiInformation;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.music.entity.Music;
import com.start.st.global.jpa.BaseEntity;
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
    @OneToOne(mappedBy = "mbtiInformation_id")
    private MbtiInformation mbtiInformation;
    @ManyToMany(mappedBy = "movie_id")
    private Set<Movie> movieList;
    @ManyToMany(mappedBy = "music_id")
    private Set<Music> musicList;

}
