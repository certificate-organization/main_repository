package com.start.st.domain.mbti.service;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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


    public List<Mbti> findAllMbti() {
        return this.mbtiRepository.findAll();
    }


    public Mbti getMbti(Long id) {
        Optional<Mbti> mbti = this.mbtiRepository.findById(id);
        if (mbti.isEmpty()) {
            return null;
        }
        return mbti.get();
    }
}
