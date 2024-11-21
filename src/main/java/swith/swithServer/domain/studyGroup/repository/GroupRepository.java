package swith.swithServer.domain.studyGroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<StudyGroup, Long> {
    Optional<StudyGroup> findByGroupIdAndGroupPw(String groupId, String groupPw);
}
