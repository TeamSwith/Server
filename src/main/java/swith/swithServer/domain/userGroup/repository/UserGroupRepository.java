package swith.swithServer.domain.userGroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.userGroup.entity.UserGroup;
import swith.swithServer.domain.user.entity.User;

import java.util.List;

public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
    boolean existsByUserAndStudyGroup(User user, StudyGroup studyGroup);

    // 그룹에 속한 모든 UserGroup 조회
    List<UserGroup> findAllByStudyGroup(StudyGroup studyGroup);
}
