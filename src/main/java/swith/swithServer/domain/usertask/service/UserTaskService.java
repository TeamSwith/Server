package swith.swithServer.domain.usertask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.usertask.entity.UserTask;
import swith.swithServer.domain.usertask.entity.TaskStatus;
import swith.swithServer.domain.usertask.repository.UserTaskRepository;

@Service
@RequiredArgsConstructor
public class UserTaskService {

    private final UserTaskRepository userTaskRepository;

    @Transactional
    public String updateTaskStatus(Long userId, Long taskId) {
        UserTask userTask = userTaskRepository.findByUserIdAndId(userId, taskId)
                .orElseThrow(() -> new IllegalArgumentException("Task not found for the given userId and taskId"));

        if (userTask.getTaskStatus() == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Task is already completed");
        }

        userTask.updateTaskStatus(TaskStatus.COMPLETED);
        return TaskStatus.COMPLETED.name();
    }
}