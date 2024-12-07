package swith.swithServer.domain.attend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.attend.entity.Attend;
import swith.swithServer.domain.attend.entity.AttendStatus;
import swith.swithServer.domain.task.dto.TaskResponse;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.usertask.entity.UserTask;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AttendStatusResponse {
    private Long userId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AttendStatus attendStatus;

    public static List<AttendStatusResponse> from(List<Attend> attends){
        List<AttendStatusResponse> attendStatusResponses = new ArrayList<>();
        for(Attend attend : attends){
            AttendStatusResponse attendStatusResponse = AttendStatusResponse.builder()
                    .userId(attend.getUser().getId())
                    .attendStatus(attend.getAttendStatus())
                    .build();
            attendStatusResponses.add(attendStatusResponse);
        }
        return attendStatusResponses;
    }
}
