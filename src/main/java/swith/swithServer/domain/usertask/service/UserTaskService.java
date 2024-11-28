package swith.swithServer.domain.usertask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.usertask.dto.UserTaskUpdateResponse;
import swith.swithServer.domain.usertask.entity.UserTask;
import swith.swithServer.domain.usertask.entity.TaskStatus;
import swith.swithServer.domain.usertask.repository.UserTaskRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.domain.user.entity.User;

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;
    private final OauthService authService;

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
}