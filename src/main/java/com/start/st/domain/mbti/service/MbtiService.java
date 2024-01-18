package com.start.st.domain.mbti.service;

import com.start.st.domain.mbti.repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MbtiService {
    private final MbtiRepository mbtiRepository;
}
