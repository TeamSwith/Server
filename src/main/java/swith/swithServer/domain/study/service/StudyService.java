package swith.swithServer.domain.study.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.study.dto.StudyRequest;
import swith.swithServer.domain.study.dto.StudyUpdateRequest;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final GroupRepository groupRepository;

    //id로 찾기
    public Study getStudyById(Long id){
        Study study=studyRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        return study;
    }

    //study 생성
    @Transactional
    public Study createStudy(StudyRequest studyRequest, Long id){
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(()->new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));
        Study study = new Study(studyRequest.getDate(), studyRequest.getTime(), studyRequest.getLocation(), studyGroup);
        return studyRepository.save(study);
    }

    //study 수정
    @Transactional
    public Study updateStudy(Long id, StudyUpdateRequest studyUpdateRequest){
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        study.updateTime(studyUpdateRequest.getTime());
        study.updateLocation(studyUpdateRequest.getLocation());
        return studyRepository.save(study);
    }

    //study 삭제
    @Transactional
    public void deleteStudy(Long id){
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        studyRepository.delete(study);
    }

}
