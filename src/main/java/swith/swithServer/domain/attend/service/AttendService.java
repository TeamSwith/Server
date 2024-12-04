package swith.swithServer.domain.attend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.attend.entity.Attend;
import swith.swithServer.domain.attend.entity.AttendStatus;
import swith.swithServer.domain.attend.repository.AttendRepository;
import swith.swithServer.domain.sse.service.SseEmitters;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class AttendService {
    private final StudyRepository studyRepository;
    private final AttendRepository attendRepository;
    private final UserGroupRepository userGroupRepository;
    private final SseEmitters sseEmitters;

    //user, study로 출석 상태 테이블 찾기
//    public Attend getAttendByUserAndStudy(User user, Study study){
//        Attend attend = attendRepository.find
//    }

    //출석 테이블 생성
    @Transactional
    public Attend createAttend(User user, Long studyId){
        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        Attend attend = new Attend(user, study);
        attend.updateAttendStatus(AttendStatus.ABSENCE);
        return attendRepository.save(attend);
    }


    //출석 테이블 삭제
    @Transactional
    public void deleteAttend(Long id){
        Attend attend = attendRepository.findById(id)
                .orElseThrow(()->new BusinessException(ErrorCode.ATTEND_DOESNT_EXIST));
        attendRepository.delete(attend);
    }


    //출석 상태 업데이트
    @Transactional
    public Attend updateAttend(User user, Long studyId, StudyGroup studyGroup){
        Study study = studyRepository.findById(studyId)
                .orElseThrow(()-> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        Attend attend = attendRepository.findByUserAndStudy(user, study)
                .orElseThrow(()->new BusinessException(ErrorCode.ATTEND_DOESNT_EXIST));
        if(attend.getAttendStatus()==AttendStatus.ATTEND)
            throw new BusinessException(ErrorCode.ALREADY_ATTEND);
        attend.updateAttendStatus(AttendStatus.ATTEND);
        attendRepository.save(attend);
        updateUserAttend(studyGroup, attend);
        return attend;
    }
//출석 상태 변화시 sse
    @Transactional
    private void updateUserAttend(StudyGroup studyGroup, Attend attend){
        userGroupRepository.findAllByStudyGroup(studyGroup)
                .forEach(userGroup -> sseEmitters.sendSse(userGroup.getUser().getId(), "Attend update", attend));
    }
}
