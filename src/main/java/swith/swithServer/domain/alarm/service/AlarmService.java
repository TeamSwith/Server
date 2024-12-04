package swith.swithServer.domain.alarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.alarm.entity.Alarm;
import swith.swithServer.domain.alarm.entity.UserAlarm;
import swith.swithServer.domain.alarm.repository.AlarmRepository;
import swith.swithServer.domain.alarm.repository.UserAlarmRepository;
import swith.swithServer.domain.alarm.dto.AlarmResponse;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final UserAlarmRepository userAlarmRepository;
    private final AlarmRepository alarmRepository;

    // 사용자 알림 조회
    public List<AlarmResponse> getUserAlarms(User user) {
        List<UserAlarm> userAlarms = userAlarmRepository.findByUser(user);

        return userAlarms.stream()
                .map(userAlarm -> new AlarmResponse(
                        userAlarm.getAlarm().getId(),
                        userAlarm.getAlarm().getContent(),
                        userAlarm.isRead(),
                        userAlarm.getAlarm().getCreatedAt().toString()
                ))
                .collect(Collectors.toList());
    }

    // 알림 읽음 처리
    @Transactional
    public UserAlarm markAlarmAsRead(Long userAlarmId) {
        UserAlarm userAlarm = userAlarmRepository.findById(userAlarmId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ALARM_NOT_FOUND));

        // 이미 읽음 상태라면 예외 발생
        if (userAlarm.isRead()) {
            throw new BusinessException(ErrorCode.ALARM_ALREADY_READ);
        }

        // 읽음 상태로 변경
        userAlarm.markAsRead();
        return userAlarmRepository.save(userAlarm);
    }
}