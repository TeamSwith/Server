package swith.swithServer.domain.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Optional<Study> findByStudyGroupAndDate(StudyGroup studyGroup, LocalDate date);
    List<Study> findAllByStudyGroup(StudyGroup studyGroup);
}