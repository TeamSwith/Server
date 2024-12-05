package swith.swithServer.domain.attend.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.attend.entity.Attend;
import swith.swithServer.domain.attend.entity.AttendStatus;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

@Getter
@AllArgsConstructor
public class SseAttendResponse {
    private Long attendId;
    private Long userId;
    private Long groupId;
    private Long studyId;
    @Enumerated(EnumType.STRING)
    private AttendStatus attendStatus;

    public static SseAttendResponse fromEntity(Attend attend, StudyGroup studyGroup) {
        return new SseAttendResponse(
                attend.getId(),
                attend.getUser().getId(),
                studyGroup.getId(),
                attend.getStudy().getId(),
                attend.getAttendStatus()
        );
    }
}
