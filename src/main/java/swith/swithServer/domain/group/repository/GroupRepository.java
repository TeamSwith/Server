package swith.swithServer.domain.group.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.group.domain.GroupDomain;

public interface GroupRepository extends JpaRepository<GroupDomain, Long> {
    boolean existsByGroupId(Long groupId);
}