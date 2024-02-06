package com.start.st.domain.member.service;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.repository.MbtiRepository;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.repository.MemberRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    public void modify(String membername, String nickname,String email, Mbti mbti, String password) {
        Optional<Member> member = this.memberRepository.findByMembername(membername);

        if (password.isEmpty()) {
            Member modifyMember = member.get().toBuilder()
                    .nickname(nickname)
                    .email(email)
                    .mbti(mbti)
                    .build();
            this.memberRepository.save(modifyMember);
        } else {
            Member modifyMember = member.get().toBuilder()
                    .nickname(nickname)
                    .email(email)
                    .mbti(mbti)
                    .password(passwordEncoder.encode(password))
                    .build();
            this.memberRepository.save(modifyMember);
        }
    }

    public Member getMember(String membername) {
        Optional<Member> member = this.memberRepository.findByMembername(membername);
        if (member.isEmpty()) {
            return null;
        }
        return member.get();
    }

    public Member findByMemberId(Long id) {
        Optional<Member> member = this.memberRepository.findById(id);
        if (member.isEmpty()) {
            return null;
        }
        return member.get();
    }

    private Optional<Member> findByMembername(String membername) {
        return memberRepository.findByMembername(membername);
    }

    public boolean paswordConfirm(String password, Member member) {
        return passwordEncoder.matches(password, member.getPassword());
    }

    @Transactional
    public Member whenSocialLogin(String providerTypeCode, String membername, String nickname) {
        Optional<Member> opMember = findByMembername(membername);
        if (opMember.isPresent()) return opMember.get();

        create(membername, "", nickname, null, null);
        return this.memberRepository.findByMembername(membername).get(); // 최초 로그인 시 딱 한번 실행
    }
}
