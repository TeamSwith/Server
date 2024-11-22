package swith.swithServer.domain.studyGroup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<StudyGroup, Long> {
    boolean existsByGroupInsertId(String groupInsertId);
    Optional<StudyGroup> findByGroupInsertIdAndGroupPw(String groupInsertId, String groupPw);
}