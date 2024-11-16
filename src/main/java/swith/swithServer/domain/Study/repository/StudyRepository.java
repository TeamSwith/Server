package swith.swithServer.domain.Study.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.Group.Entity.Group;
import swith.swithServer.domain.Study.Entity.Study;

import java.util.Optional;


public interface StudyRepository extends JpaRepository<Study, Long>{
}
