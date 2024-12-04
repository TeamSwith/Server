package swith.swithServer.domain.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.alarm.entity.Alarm;

@Getter
@AllArgsConstructor
public class AlarmDeleteResponse {
    private Long id; // 알림 ID
    private String content; // 알림 내용
    private String createdAt; // 생성 날짜

    public static AlarmDeleteResponse fromEntity(Alarm alarm) {
        return new AlarmDeleteResponse(
                alarm.getId(),
                alarm.getContent(),
                alarm.getCreatedAt().toString() // LocalDateTime을 String으로 변환
        );
    }
}