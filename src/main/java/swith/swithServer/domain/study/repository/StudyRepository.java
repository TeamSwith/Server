package swith.swithServer.domain.study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.study.entity.Study;

public interface StudyRepository extends JpaRepository<Study, Long> {
}