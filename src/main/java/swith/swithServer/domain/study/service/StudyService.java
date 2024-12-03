package swith.swithServer.domain.study.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.attend.service.AttendService;
import swith.swithServer.domain.comment.entity.Comment;
import swith.swithServer.domain.comment.repository.CommentRepository;
import swith.swithServer.domain.comment.service.CommentService;
import swith.swithServer.domain.study.dto.StudyRequest;
import swith.swithServer.domain.study.dto.StudyUpdateRequest;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.task.repository.TaskRepository;
import swith.swithServer.domain.task.service.TaskService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.entity.UserGroup;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudyService {
    private final StudyRepository studyRepository;
    private final GroupRepository groupRepository;
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private final CommentService commentService;
    private final UserGroupRepository userGroupRepository;
    private final AttendService attendService;

    //id로 찾기
    public Study getStudyById(Long id){
        Study study=studyRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        return study;
    }
    //그룹, 날짜로 스터디 일정 찾기
    public Study getStudyByGroupDate(Long groupId, LocalDate date) {
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(()-> new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));
        Study study = studyRepository.findByStudyGroupAndDate(studyGroup, date)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        return study;
    }

    //study 생성
    @Transactional
    public Study createStudy(StudyRequest studyRequest, Long id){
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(()->new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));
        if(studyRepository.findByStudyGroupAndDate(studyGroup, studyRequest.getDate()).isPresent()){
            throw new BusinessException(ErrorCode.STUDY_EXIST);
        }
        Study study = new Study(studyRequest.getDate(), studyRequest.getTime(), studyRequest.getLocation(), studyGroup);
        List<UserGroup> userList = userGroupRepository.findAllByStudyGroup(studyGroup);
        studyRepository.save(study);
        for(UserGroup userGroup : userList){
            attendService.createAttend(userGroup.getUser(), study.getId());
        }
        return study;
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
        //일정 삭제시 출석 상태, 코멘트, 과제 테이블 삭제
//출석 상태는 아직 없어서 추후에 추가
        List<Comment> comments = commentRepository.findByStudyIdOrderByIdAsc(id);
        for(Comment comment : comments){
            commentService.deleteComment(comment.getId());
        }
        List<Task> tasks = taskRepository.findByStudy(study);
        for(Task task : tasks){
            taskService.deleteTask(task.getId());
        }
        studyRepository.delete(study);
    }

}
