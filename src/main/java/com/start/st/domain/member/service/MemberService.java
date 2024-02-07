package com.start.st.domain.member.service;

import com.start.st.domain.email.service.EmailService;
import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.member.entity.Member;
import com.start.st.domain.member.repository.MemberRepository;
import com.start.st.global.markdown.CommonUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final CommonUtil commonUtil;

    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public void create(String membername, String password, String nickname, String email,
                       Mbti mbti, MultipartFile memberImg) {
        String thumbnailRelPath = "";
        if (memberImg.isEmpty()) {
            thumbnailRelPath = null;
        } else {
            thumbnailRelPath = "member/" + UUID.randomUUID().toString() + ".jpg";
            File thumbnailFile = new File(fileDirPath + "/" + thumbnailRelPath);

            try {
                memberImg.transferTo(thumbnailFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        Member member = Member.builder()
                .membername(membername)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .mbti(mbti)
                .memberImg(thumbnailRelPath)
                .build();

        this.memberRepository.save(member);
    }

    public void modify(String membername, String nickname, String email, Mbti mbti, String password,
                       MultipartFile memberImg) {
        Optional<Member> member = this.memberRepository.findByMembername(membername);

        String thumbnailRelPath = "";
        if (memberImg.isEmpty()) {
            thumbnailRelPath = null;
        } else {
            thumbnailRelPath = "member/" + UUID.randomUUID().toString() + ".jpg";
            File thumbnailFile = new File(fileDirPath + "/" + thumbnailRelPath);

            try {
                memberImg.transferTo(thumbnailFile);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (password.isEmpty()) {
            Member modifyMember = member.get().toBuilder()
                    .nickname(nickname)
                    .email(email)
                    .mbti(mbti)
                    .memberImg(thumbnailRelPath)
                    .build();
            this.memberRepository.save(modifyMember);
        } else {
            Member modifyMember = member.get().toBuilder()
                    .nickname(nickname)
                    .email(email)
                    .mbti(mbti)
                    .password(passwordEncoder.encode(password))
                    .memberImg(thumbnailRelPath)
                    .build();
            this.memberRepository.save(modifyMember);
        }
    }

    public Member findByMemberId(Long id) {
        Optional<Member> member = this.memberRepository.findById(id);
        if (member.isEmpty()) {
            return null;
        }
        return member.get();
    }

    public Member getMember(String membername) {
        Optional<Member> member = this.memberRepository.findByMembername(membername);
        if (member.isEmpty()) {
            return null;
        }
        return member.get();
    }

    public Member findByNickname(String nickname) {
        Optional<Member> member = this.memberRepository.findByNickname(nickname);
        if (member.isEmpty()) {
            return null;
        }
        return member.get();
    }

    public Member findByEmail(String email) {
        Optional<Member> member = this.memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            return null;
        }
        return member.get();
    }

    public boolean paswordConfirm(String password, Member member) {
        return passwordEncoder.matches(password, member.getPassword());
    }

    @Transactional
    public Member whenSocialLogin(String providerTypeCode, String membername, String nickname) {
        Member member = getMember(membername);
        if (member != null) {
            return member;
        }

        create(membername, "", nickname, null, null, null);
        return this.memberRepository.findByMembername(membername).get(); // 최초 로그인 시 딱 한번 실행
    }
    @Transactional
    public void modifyPassword(String email) throws EmailService.EmailException {
        SecureRandom random = new SecureRandom();
        String tempPassword = generateRandomPassword(random, 8);
        Member member = this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 이메일의 유저가 없습니다."));
        Member newMember = member.toBuilder()
                .password(passwordEncoder.encode(tempPassword))
                .build();
        this.memberRepository.save(newMember);
        emailService.sendSimpleMessage(email, tempPassword);
    }

    public static String generateRandomPassword(SecureRandom random, int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

}
