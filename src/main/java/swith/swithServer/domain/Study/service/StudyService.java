package swith.swithServer.domain.Study.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.Study.Entity.Study;
import swith.swithServer.domain.Study.repository.StudyRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;


    //study 생성
    @Transactional
    public Study createStudy(Study study){
        return studyRepository.save(study);
    }

    //study 수정
    @Transactional
    public Study updateStudy(Long id, LocalDateTime time, String location){
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Study ID: "+id));
        study.updateTime(time);
        study.updateLocation(location);
        return studyRepository.save(study);
    }

    //study 삭제
    @Transactional
    public void deleteStudy(Long id){
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Study ID: "+id));
        studyRepository.delete(study);
    }

}
