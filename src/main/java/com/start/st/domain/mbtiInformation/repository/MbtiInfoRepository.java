package com.start.st.domain.mbtiInformation.repository;

import com.start.st.domain.mbtiInformation.entity.MbtiInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbtiInfoRepository extends JpaRepository<MbtiInfo, Long> {
}
