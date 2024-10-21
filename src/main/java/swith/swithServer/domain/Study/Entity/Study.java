package swith.swithServer.domain.Study.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import swith.swithServer.domain.Group.Entity.Group;
import swith.swithServer.domain.common.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long study_id;

    @NotNull
    private LocalDate date;

    private LocalDateTime time;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group_id;

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setLocation(String location){
        this.location = location;
    }
}
