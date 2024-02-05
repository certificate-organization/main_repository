package com.start.st.domain.member.repository;

import com.start.st.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findById(Long id);
    Optional<Member> findByMembername(String membername);
    Optional<Member> findByNickname(String nickname);
    Optional<Member> findByEmail(String email);
}
