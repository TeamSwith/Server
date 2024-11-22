package swith.swithServer.domain.usertask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.usertask.entity.UserTask;
import swith.swithServer.domain.usertask.entity.TaskStatus;
import swith.swithServer.domain.usertask.repository.UserTaskRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;

    @Transactional
    public String updateTaskStatus(Long userId, Long taskId) {
        UserTask userTask = userTaskRepository.findByUserIdAndId(userId, taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_TASK_NOT_FOUND));

        if (userTask.getTaskStatus() == TaskStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.TASK_ALREADY_COMPLETED);
        }

        userTask.updateTaskStatus(TaskStatus.COMPLETED);
        return TaskStatus.COMPLETED.name();
    }

    @Transactional
    public String updateTaskStatusToPending(Long userId, Long taskId) {
        UserTask userTask = userTaskRepository.findByUserIdAndId(userId, taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_TASK_NOT_FOUND));

        if (userTask.getTaskStatus() != TaskStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.TASK_NOT_COMPLETED);
        }

        userTask.updateTaskStatus(TaskStatus.PENDING);
        return TaskStatus.PENDING.name();
    }
}