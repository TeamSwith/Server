package swith.swithServer.domain.UserGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.Group.Entity.Group;
import swith.swithServer.domain.UserGroup.entity.UserGroup;
import swith.swithServer.domain.user.entity.User;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    boolean existsByUserAndGroup(User user, Group group);
}
