package swith.swithServer.domain.usertask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.usertask.entity.UserTask;
import swith.swithServer.domain.task.entity.Task;

import java.util.List;
import java.util.Optional;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    Optional<UserTask> findByUserIdAndId(Long userId, Long taskId);
    List<UserTask> findAllByTask(Task task);
    Optional<UserTask> findByUserAndTask(User user, Task task);
}