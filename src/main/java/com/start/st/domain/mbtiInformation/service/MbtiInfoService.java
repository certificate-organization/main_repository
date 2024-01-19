package com.start.st.domain.mbtiInformation.service;

import com.start.st.domain.mbtiInformation.entity.MbtiInformation;
import com.start.st.domain.mbtiInformation.repository.MbtiInformationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MbtiInfoService {
    private final MbtiInformationRepository mbtiInformationRepository;

    public void create(String content) {
        MbtiInformation mbtiInformation = MbtiInformation.builder()
                .content(content)
                .build();

        this.mbtiInformationRepository.save(mbtiInformation);
    }
}
