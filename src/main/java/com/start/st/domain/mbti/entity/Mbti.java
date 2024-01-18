package com.start.st.domain.mbti.entity;

import com.start.st.domain.mbtiInformation.entity.MbtiInfo;
import com.start.st.domain.movie.Movie;
import com.start.st.domain.music.Music;
import com.start.st.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    @OneToMany(mappedBy = "mbti", cascade = CascadeType.REMOVE)
    private List<Movie> movieList;
    @OneToMany(mappedBy = "mbti", cascade = CascadeType.REMOVE)
    private List<Music> musicList;
}
