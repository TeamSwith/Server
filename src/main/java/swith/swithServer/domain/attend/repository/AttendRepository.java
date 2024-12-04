package swith.swithServer.domain.attend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.attend.entity.Attend;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.user.entity.User;

import java.util.List;
import java.util.Optional;

public interface AttendRepository extends JpaRepository<Attend, Long> {
    Optional<Attend> findByUserAndStudy(User user, Study study);
    List<Attend> findByStudy(Study study);
}
