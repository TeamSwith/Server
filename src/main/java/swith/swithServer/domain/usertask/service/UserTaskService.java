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

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final OauthService authService;
    private final UserRepository userRepository;
    private final GroupRepository studyGroupRepository;
    private final TaskRepository taskRepository;

    @Transactional
    public UserTaskUpdateResponse updateTaskStatus(Long taskId, String taskStatus) {
        User loginUser = authService.getLoginUser();
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.USER_DOESNT_EXIST);
        }

        TaskStatus newStatus;
        try {
            newStatus = TaskStatus.valueOf(taskStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }

        UserTask userTask = userTaskRepository.findByUserIdAndId(loginUser.getId(), taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_TASK_NOT_FOUND));

        if (newStatus == TaskStatus.COMPLETED && userTask.getTaskStatus() == TaskStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.TASK_ALREADY_COMPLETED);
        }

        if (newStatus == TaskStatus.PENDING && userTask.getTaskStatus() != TaskStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.TASK_NOT_COMPLETED);
        }

        userTask.updateTaskStatus(newStatus);
        return new UserTaskUpdateResponse(userTask.getId(), newStatus.name());
    }

    @Transactional
    public UserTask createUserTask(Long userId, Long taskId, Long groupId, String taskStatus) {
        // User 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));

        // StudyGroup 검증
        StudyGroup studyGroup = studyGroupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));

        // Task 검증
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.TASK_DOESNT_EXIST));

        // TaskStatus 변환
        TaskStatus status;
        try {
            status = TaskStatus.valueOf(taskStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }

        // UserTask 생성 및 저장
        UserTask userTask = new UserTask(studyGroup, user, task, status);
        return userTaskRepository.save(userTask);
    }
}