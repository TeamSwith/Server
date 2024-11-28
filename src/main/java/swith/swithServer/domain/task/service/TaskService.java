package swith.swithServer.domain.task.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.domain.studyGroup.dto.StringRequest;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.task.repository.TaskRepository;
import swith.swithServer.domain.usertask.entity.UserTask;
import swith.swithServer.domain.usertask.service.UserTaskService;
import swith.swithServer.domain.usertask.repository.UserTaskRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.domain.usertask.entity.TaskStatus;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final StudyRepository studyRepository;
    private final UserTaskRepository userTaskRepository;
    private final UserGroupRepository userGroupRepository;
    private final UserTaskService userTaskService;


    //study로 과제 찾기
    public List<Task> getTaskByStudy(Study study){
        List<Task> task=taskRepository.findByStudy(study);
        return task;
    }

    //과제 생성
    @Transactional
    public Task createTask(StudyGroup studyGroup, Long id, StringRequest stringRequest){
        Study study = studyRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));
        Task task = new Task(stringRequest.getMessage(), study);
        Task savedTask = taskRepository.save(task);

        // UserTask 생성 (매핑 테이블 데이터 생성)
        userGroupRepository.findAllByStudyGroup(studyGroup).forEach(userGroup -> {
            UserTask userTask = new UserTask(studyGroup, userGroup.getUser(), savedTask, TaskStatus.PENDING);
            userTaskRepository.save(userTask); // UserTask 저장
        });
        return savedTask;
    }

    //과제 삭제
    @Transactional
    public void deleteTask(Long id){

        userTaskService.deleteUserTasksByTaskId(id);
        Task task = taskRepository.findById(id)
                .orElseThrow(()-> new BusinessException(ErrorCode.TASK_DOESNT_EXIST));
        taskRepository.delete(task);

    }


}
