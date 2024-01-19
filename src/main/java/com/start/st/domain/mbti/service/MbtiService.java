package com.start.st.domain.mbti.service;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MbtiService {
    private final MbtiRepository mbtiRepository;

    public void create(String name) {
        Mbti mbti = Mbti.builder()
                .name(name)
                .build();
        this.mbtiRepository.save(mbti);
    }
}
