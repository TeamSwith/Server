package swith.swithServer.domain.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.alarm.entity.UserAlarm;

@Getter
@AllArgsConstructor
public class AlarmResponse {
    private Long id; // 알림 ID
    private String content; // 알림 내용
    private boolean isRead; // 읽음 여부
    private String createdAt; // 생성 날짜

    public static AlarmResponse fromEntity(UserAlarm userAlarm) {
        return new AlarmResponse(
                userAlarm.getId(),
                userAlarm.getAlarm().getContent(),
                userAlarm.isRead(),
                userAlarm.getCreatedAt().toString() // LocalDateTime을 String으로 변환
        );
    }
}