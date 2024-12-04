package swith.swithServer.domain.alarm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.alarm.entity.UserAlarm;
import swith.swithServer.domain.alarm.entity.Alarm;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

@Getter
@AllArgsConstructor
public class AlarmResponse {
    private Long id; // 알림 ID
    private Long groupId; // 그룹 ID
    private String content; // 알림 내용
    private boolean isRead; // 읽음 여부
    private String createdAt; // 생성 날짜

    public static AlarmResponse fromEntity(UserAlarm userAlarm) {
        if (userAlarm.getAlarm().getGroupAlarms().isEmpty()) {
            throw new BusinessException(ErrorCode.GROUP_ID_NOT_FOUND); // ErrorCode에 적절한 값 추가 필요
        }

        Long groupId = userAlarm.getAlarm().getGroupAlarms().get(0).getStudyGroup().getId();
        return new AlarmResponse(
                userAlarm.getId(),
                groupId,
                userAlarm.getAlarm().getContent(),
                userAlarm.isRead(),
                userAlarm.getCreatedAt().toString() // LocalDateTime을 String으로 변환
        );
    }
}