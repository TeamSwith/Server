package swith.swithServer.domain.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.task.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
