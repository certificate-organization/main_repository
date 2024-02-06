package com.start.st.domain.mbti.repository;

import com.start.st.domain.mbti.entity.Mbti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    Optional<Mbti> findByName(String name);
}
