package swith.swithServer.domain.alarm.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.alarm.entity.Alarm;
import swith.swithServer.domain.alarm.entity.GroupAlarm;

public interface GroupAlarmRepository extends JpaRepository<GroupAlarm, Long> {
    void deleteByAlarm(Alarm alarm);
}