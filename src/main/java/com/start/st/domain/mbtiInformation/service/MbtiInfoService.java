package com.start.st.domain.mbtiInformation.service;

import com.start.st.domain.mbtiInformation.entity.MbtiInfo;
import com.start.st.domain.mbtiInformation.repository.MbtiInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MbtiInfoService {
    private final MbtiInfoRepository mbtiInfoRepository;

    public void create(String content) {
        MbtiInfo mbtiInfo = MbtiInfo.builder()
                .content(content)
                .build();

        this.mbtiInfoRepository.save(mbtiInfo);
    }
}
