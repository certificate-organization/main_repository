package com.start.st.domain.mbti.entity;

import com.start.st.domain.mbtiInformation.entity.MbtiInformation;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.movie.entity.Movie;
import com.start.st.domain.music.entity.Music;
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
    @OneToOne(mappedBy = "mbti") //정보는 mbti에 의존적
    private MbtiInformation mbtiInformation;
    @OneToMany(mappedBy = "mbti", cascade = CascadeType.REMOVE)
    private List<Movie> movieList;
    @OneToMany(mappedBy = "mbti", cascade = CascadeType.REMOVE)
    private List<Music> musicList;
    @OneToMany(mappedBy = "mbti")
    private List<Member> memberList;
}
