package swith.swithServer.domain.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.task.entity.Task;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStudy(Study study);
}
