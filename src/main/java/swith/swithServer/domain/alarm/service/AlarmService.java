package swith.swithServer.domain.alarm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.alarm.dto.AlarmDeleteResponse;
import swith.swithServer.domain.alarm.entity.Alarm;
import swith.swithServer.domain.alarm.entity.GroupAlarm;
import swith.swithServer.domain.alarm.entity.UserAlarm;
import swith.swithServer.domain.alarm.repository.AlarmRepository;
import swith.swithServer.domain.alarm.repository.GroupAlarmRepository;
import swith.swithServer.domain.alarm.repository.UserAlarmRepository;
import swith.swithServer.domain.alarm.dto.AlarmResponse;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.entity.UserGroup;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.global.oauth.service.OauthService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {

    private final UserAlarmRepository userAlarmRepository;
    private final AlarmRepository alarmRepository;
    private final GroupAlarmRepository groupAlarmRepository;
    private final UserGroupRepository userGroupRepository;
    private final OauthService authService;


    // 사용자 알림 조회
    public List<AlarmResponse> getUserAlarms(User user) {
        List<UserAlarm> userAlarms = userAlarmRepository.findByUser(user);
        return userAlarms.stream()
                .map(AlarmResponse::fromEntity)
                .collect(Collectors.toList());

    }

    // 알림 읽음 처리
    @Transactional
    public UserAlarm markAlarmAsRead(Long userAlarmId) {
        User user = authService.getLoginUser(); // 로그인된 유저 가져오기

        UserAlarm userAlarm = userAlarmRepository.findById(userAlarmId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ALARM_NOT_FOUND));

        if (!userGroupRepository.existsByUserAndStudyGroup(user, userAlarm.getAlarm().getGroupAlarms().get(0).getStudyGroup()
        )) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }

        if (userAlarm.isRead()) {
            throw new BusinessException(ErrorCode.ALARM_ALREADY_READ);
        }
        userAlarm.markAsRead();
        return userAlarmRepository.save(userAlarm);
    }

    // 매핑 테이블 생성
    @Transactional
    public Alarm createGroupAndUserAlarms(String content, StudyGroup studyGroup) {
        // Alarm 생성
        Alarm alarm = Alarm.builder()
                .content(content)
                .build();
        alarmRepository.save(alarm);

        // GroupAlarm 생성
        GroupAlarm groupAlarm = GroupAlarm.builder()
                .alarm(alarm)
                .studyGroup(studyGroup)
                .build();
        groupAlarmRepository.save(groupAlarm);

        // StudyGroup에 속한 모든 사용자에게 UserAlarm 생성
        List<UserGroup> userGroups = userGroupRepository.findAllByStudyGroup(studyGroup);
        for (UserGroup userGroup : userGroups) {
            createUserAlarm(alarm, userGroup.getUser());
        }

        return alarm;
    }

    @Transactional
    public void createUserAlarm(Alarm alarm, User user) {
        UserAlarm userAlarm = UserAlarm.builder()
                .alarm(alarm)
                .user(user)
                .isRead(false)
                .build();
        userAlarmRepository.save(userAlarm);
    }


    // 알림 삭제
    @Transactional
    public AlarmDeleteResponse deleteAlarm(Long alarmId) {

        User user = authService.getLoginUser();

        Alarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ALARM_NOT_FOUND));

        // 권한확인
        GroupAlarm groupAlarm = alarm.getGroupAlarms().stream()
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ALARM_NOT_FOUND));

        StudyGroup studyGroup = groupAlarm.getStudyGroup();

        if (!userGroupRepository.existsByUserAndStudyGroup(user, studyGroup)) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }

        userAlarmRepository.deleteByAlarm(alarm);
        groupAlarmRepository.deleteByAlarm(alarm);

        alarmRepository.delete(alarm);
        return AlarmDeleteResponse.fromEntity(alarm);

    }
}

