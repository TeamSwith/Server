package swith.swithServer.domain.Group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.Group.Entity.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {
    Optional<Group> findByGroupIdAndGroupPw(String groupId, String groupPw);
}
