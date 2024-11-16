package swith.swithServer.domain.study.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.common.BaseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@RequiredArgsConstructor
public class Study extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    @Schema(type = "LocalTime")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
    private StudyGroup studyGroup;


    public Study(LocalDate date, LocalTime time, String location, StudyGroup studyGroup){
        this.date = date;
        this.time = time;
        this.location = location;
        this.studyGroup = studyGroup;
    }
    public void updateTime(LocalTime time) {
        this.time = time;
    }

    public void updateLocation(String location){
        this.location = location;
    }
}
