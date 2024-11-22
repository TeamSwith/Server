package swith.swithServer.domain.usertask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.usertask.entity.UserTask;

import java.util.Optional;

public interface UserTaskRepository extends JpaRepository<UserTask, Long> {
    Optional<UserTask> findByUserIdAndId(Long userId, Long taskId);
}