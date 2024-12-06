package swith.swithServer.domain.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface StudyRepository extends JpaRepository<Study, Long> {
    Optional<Study> findByStudyGroupAndDate(StudyGroup studyGroup, LocalDate date);
    List<Study> findAllByStudyGroup(StudyGroup studyGroup);

    @Query("SELECT FUNCTION('DAY', s.date) FROM Study s WHERE s.studyGroup = :studyGroup AND FUNCTION('YEAR', s.date) = :year AND FUNCTION('MONTH', s.date) = :month")
    List<Integer> findByStudyGroupAndYearAndDate(StudyGroup studyGroup,String year,String month);
}