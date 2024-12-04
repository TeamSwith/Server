package swith.swithServer.domain.alarm.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.alarm.dto.AlarmDeleteResponse;
import swith.swithServer.domain.alarm.dto.AlarmResponse;
import swith.swithServer.domain.alarm.service.AlarmService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.global.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmService alarmService;
    private final OauthService authService;

    // 사용자 알림 목록 조회
    @GetMapping
    public ApiResponse<List<AlarmResponse>> getUserNotifications() {
        User loginUser = authService.getLoginUser();
        List<AlarmResponse> alarms = alarmService.getUserAlarms(loginUser);
        return new ApiResponse<>(200, alarms);
    }
    // 알림 읽음 처리
    @PatchMapping("/{alarmId}/read")
    public ApiResponse<AlarmResponse> markAlarmAsRead(@PathVariable Long alarmId) {
        var userAlarm = alarmService.markAlarmAsRead(alarmId);
        return new ApiResponse<>(200, AlarmResponse.fromEntity(userAlarm));
    }

    // 알람 삭제

    @DeleteMapping("/{alarmId}")
    public ApiResponse<AlarmDeleteResponse> deleteAlarm(@PathVariable Long alarmId) {
        AlarmDeleteResponse deletedAlarm = alarmService.deleteAlarm(alarmId);
        return new ApiResponse<>(200, deletedAlarm);
    }
}