package com.start.st.domain.mbti.service;

import com.start.st.domain.mbti.entity.Mbti;
import com.start.st.domain.mbti.repository.MbtiRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MbtiService {
    private final MbtiRepository mbtiRepository;

    //MBTI요소
    public List<String> getElements(String name) {
        List<String> elements = new ArrayList<>();
        for (int i = 0; i < name.length(); i++) {
            switch (name.charAt(i)) {
                case 'I':
                    elements.add("Introversion(내향)");
                    break;
                case 'E':
                    elements.add("Extraversion(외향)");
                    break;
                case 'S':
                    elements.add("Sensing(감각)");
                    break;
                case 'N':
                    elements.add("iNtuition(직관)");
                    break;
                case 'T':
                    elements.add("Thinking(사고)");
                    break;
                case 'F':
                    elements.add("Felling(감정)");
                    break;
                case 'J':
                    elements.add("Judging(판단)");
                    break;
                case 'P':
                    elements.add("Perceiving(인식)");
                    break;
            }
        }
        return elements;
    }

    //만들어질 때, 요소도 같이 만들어짐
    public void create(String name) {
        List<String> elements = this.getElements(name);
        Mbti mbti = Mbti.builder()
                .name(name)
                .elements(elements)
                .build();
        this.mbtiRepository.save(mbti);
    }

    public void modify(Mbti mbti, String love, String relationship, String celebrity, String job) {
        Mbti modifyMbti = mbti.toBuilder()
                .love(love)
                .relationship(relationship)
                .celebrity(celebrity)
                .job(job)
                .build();
        this.mbtiRepository.save(modifyMbti);
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

    public Mbti findMbtiByName(String name) {
        Optional<Mbti> mbti = this.mbtiRepository.findByName(name);
        if (mbti.isEmpty()) {
            return null;
        }
        return mbti.get();
    }
}
