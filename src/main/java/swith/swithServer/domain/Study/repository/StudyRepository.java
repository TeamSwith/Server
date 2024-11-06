package swith.swithServer.domain.Study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.Study.Entity.Study;


public interface StudyRepository extends JpaRepository<Study, Long>{
}
