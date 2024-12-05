package swith.swithServer.domain.attend.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.attend.entity.Attend;
import swith.swithServer.domain.attend.entity.AttendStatus;

@Getter
@Builder
public class AttendResponse {
    private Long id;

    @Enumerated(EnumType.STRING)
    private AttendStatus attendStatus;

    public static AttendResponse from(Attend attend){
        return AttendResponse.builder()
                .id(attend.getId())
                .attendStatus(attend.getAttendStatus())
                .build();
    }
}
