package swith.swithServer.domain.attend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.common.BaseEntity;
import swith.swithServer.domain.user.entity.User;


@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Attend extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

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
