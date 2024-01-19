package com.start.st.domain.mbtiInformation.repository;

import com.start.st.domain.mbtiInformation.entity.MbtiInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MbtiInformationRepository extends JpaRepository<MbtiInformation, Long> {
}
