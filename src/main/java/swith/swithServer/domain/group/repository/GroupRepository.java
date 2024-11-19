package swith.swithServer.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.group.entity.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {
    boolean existsByGroupInsertId(String groupInsertId);
}