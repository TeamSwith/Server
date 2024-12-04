package swith.swithServer.domain.alarm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.alarm.entity.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
}