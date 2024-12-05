package swith.swithServer.domain.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.alarm.entity.Alarm;

@Getter
@AllArgsConstructor
public class SseAlarmResponse {
    private Long id; // 알림 ID
    private String content; // 알림 내용
    private String createdAt; // 생성 날짜
    private Long groupId; // 그룹 ID

    public static SseAlarmResponse fromEntity(Alarm alarm, Long groupId) {
        return new SseAlarmResponse(
                alarm.getId(),
                alarm.getContent(),
                alarm.getCreatedAt().toString(), // LocalDateTime -> String 변환
                groupId
        );
    }
}