package swith.swithServer.domain.alarm.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.alarm.entity.UserAlarm;
import swith.swithServer.domain.user.entity.User;

import java.util.List;

public interface UserAlarmRepository extends JpaRepository<UserAlarm, Long> {
    List<UserAlarm> findByUserIdOrderByIdDesc(Long userId);
    List<UserAlarm> findByUser(User user);
}