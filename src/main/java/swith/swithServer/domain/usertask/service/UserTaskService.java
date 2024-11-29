package swith.swithServer.domain.usertask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.task.repository.TaskRepository;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.domain.usertask.dto.UserTaskUpdateResponse;
import swith.swithServer.domain.usertask.entity.UserTask;
import swith.swithServer.domain.usertask.entity.TaskStatus;
import swith.swithServer.domain.usertask.repository.UserTaskRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.domain.user.entity.User;


import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final OauthService authService;
    private final UserRepository userRepository;
    private final GroupRepository studyGroupRepository;
    private final TaskRepository taskRepository;


    //user랑 task로 usertask 찾기
    public UserTask getUserTaskByUserAndTask(User user, Task task) {
        UserTask userTask = userTaskRepository.findByUserAndTask(user,task)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_TASK_NOT_FOUND));
        return userTask;
    }

    //user랑 tasklislt로 usertask 찾기
    public List<UserTask> getUserTaskByUserAndTaskList(User user, List<Task> tasks){
        List<UserTask> userTaskList = new ArrayList<>();
        for( Task task : tasks){
            UserTask userTask = userTaskRepository.findByUserAndTask(user, task)
                    .orElseThrow(()->new BusinessException(ErrorCode.USER_TASK_NOT_FOUND));
            userTaskList.add(userTask);
        }
        return userTaskList;
    }



    // 과제 상태 Update
    @Transactional
    public UserTaskUpdateResponse updateTaskStatus(Long taskId, String taskStatus) {
        User user = authService.getLoginUser();

        // Task 조회
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_DOESNT_EXIST));

        // UserTask 조회
        UserTask userTask = userTaskRepository.findByUserAndTask(user, task)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_TASK_NOT_FOUND));

        TaskStatus newStatus;
        try {
            newStatus = TaskStatus.valueOf(taskStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }


        // 상태 전환 검증
        if (newStatus == TaskStatus.COMPLETED && userTask.getTaskStatus() == TaskStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.TASK_ALREADY_COMPLETED);
        }

        if (newStatus == TaskStatus.PENDING && userTask.getTaskStatus() != TaskStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.TASK_NOT_COMPLETED);
        }

        // 상태 업데이트
        userTask.updateTaskStatus(newStatus);

        // 결과 반환
        return new UserTaskUpdateResponse(userTask.getId(), newStatus.name());
    }

    // UserTask 생성
    @Transactional
    public UserTask createUserTask(Long userId, Long taskId, Long groupId, String taskStatus) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));

        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_DOESNT_EXIST));

        TaskStatus status;
        try {
            status = TaskStatus.valueOf(taskStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }

        UserTask userTask = new UserTask(studyGroup, user, task, status);
        return userTaskRepository.save(userTask);
    }

    // UserTask 삭제
    @Transactional
    public void deleteUserTasksByTaskId(Long task_id) {
        Task task = taskRepository.findById(task_id)
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_DOESNT_EXIST));

        List<UserTask> userTasks = userTaskRepository.findAllByTask(task);
        userTaskRepository.deleteAllInBatch(userTasks);
    }
}