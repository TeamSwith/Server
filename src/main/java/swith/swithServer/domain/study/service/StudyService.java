package swith.swithServer.domain.study.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.repository.StudyRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final GroupRepository groupRepository;


    //id로 찾기
    public Optional<Study> getStudyById(Long id){
        return studyRepository.findById(id);
    }

    //study 생성
/*    @Transactional
    public Study createStudy(Study study){
        return studyRepository.save(study);
    }*/

    @Transactional
    public Study createStudy(LocalDate date, LocalTime time, String location, Long id){
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("Group not found"));
        Study study = new Study(date, time, location, studyGroup);
        return studyRepository.save(study);
    }

    //study 수정
    @Transactional
    public Study updateStudy(Long id, LocalTime time, String location){
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
