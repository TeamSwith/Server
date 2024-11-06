package swith.swithServer.domain.Attend;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swith.swithServer.domain.Study.Entity.Study;
import swith.swithServer.domain.common.BaseEntity;
import swith.swithServer.domain.user.entity.User;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Attend extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyId", nullable = false)
    private Study study;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AttendStatus attendStatus;

    public void updateAttendStatus(AttendStatus attendStatus) {
        this.attendStatus = attendStatus;
    }
}
