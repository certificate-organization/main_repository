package com.start.st.domain.member.service;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.repository.MbtiRepository;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.repository.MemberRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final MbtiRepository mbtiRepository;

    public void create(String membername, String password, String nickname, String email,
                       Mbti mbti) {

        Member member = Member.builder()
                .membername(membername)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .mbti(mbti)
                .build();

        this.memberRepository.save(member);
    }

    public Member getMember(String membername) {
        Optional<Member> member = this.memberRepository.findByMembername(membername);
        if (member.isEmpty()) {
            return null;
        }
        return member.get();
    }

//    public boolean isMembernameExists(String membername) {
//        return this.memberRepository.existsByMembername(membername);
//    }
//
//    public boolean isNicknameExists(String nickname) {
//        return this.memberRepository.existByNickname(nickname);
//    }
//
//    public boolean isEmailExists(String email) {
//        return this.memberRepository.existByEmail(email);
//    }
@Transactional
public Member whenSocialLogin(String providerTypeCode, String membername, String nickname) {
    Optional<Member> opMember = findByMembername(membername);
    Optional<Mbti> mbti = this.mbtiRepository.findById(1L);
    if (opMember.isPresent()) return opMember.get();

    // 소셜 로그인를 통한 가입시 비번은 없다.
    create(membername, "", nickname, "",mbti.get());
    return this.memberRepository.findByMembername(membername).get(); // 최초 로그인 시 딱 한번 실행
}

    private Optional<Member> findByMembername(String membername) {
        return memberRepository.findByMembername(membername);
    }
}
