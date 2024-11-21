package swith.swithServer.domain.usergroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swith.swithServer.domain.usergroup.entity.UserGroup;

import java.util.List;

@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    boolean existsByStudyGroupId(Long id);

    List<UserGroup> findByStudyGroupId(Long id);
}