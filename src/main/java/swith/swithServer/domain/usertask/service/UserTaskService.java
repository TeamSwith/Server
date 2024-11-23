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
    public String updateTaskStatus(Long userId, Long taskId, String taskStatus) {
        TaskStatus newStatus;
        try {
            newStatus = TaskStatus.valueOf(taskStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }

        UserTask userTask = userTaskRepository.findByUserIdAndId(userId, taskId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_TASK_NOT_FOUND));

        if (newStatus == TaskStatus.COMPLETED && userTask.getTaskStatus() == TaskStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.TASK_ALREADY_COMPLETED);
        }

        if (newStatus == TaskStatus.PENDING && userTask.getTaskStatus() != TaskStatus.COMPLETED) {
            throw new BusinessException(ErrorCode.TASK_NOT_COMPLETED);
        }

        userTask.updateTaskStatus(newStatus);
        return newStatus.name();
    }
}
//    @Transactional
//    public String updateTaskStatus(Long userId, Long taskId, String taskStatus) {
//        TaskStatus newStatus;
//        try {
//            newStatus = TaskStatus.valueOf(taskStatus.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
//        }
//
//        UserTask userTask = userTaskRepository.findByUserIdAndId(userId, taskId)
//                .orElseThrow(() -> new BusinessException(ErrorCode.USER_TASK_NOT_FOUND));
//
//        if (newStatus == TaskStatus.COMPLETED && userTask.getTaskStatus() == TaskStatus.COMPLETED) {
//            throw new BusinessException(ErrorCode.TASK_ALREADY_COMPLETED);
//        }
//
//        if (newStatus == TaskStatus.PENDING && userTask.getTaskStatus() != TaskStatus.COMPLETED) {
//            throw new BusinessException(ErrorCode.TASK_NOT_COMPLETED);
//        }
//
//        userTask.updateTaskStatus(newStatus);
//        return newStatus.name();
//    }
// }