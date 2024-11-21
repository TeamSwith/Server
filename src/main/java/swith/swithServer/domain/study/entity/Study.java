package swith.swithServer.domain.study.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.common.BaseEntity;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "study_id")
    private Long id;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "12:00:00")
    private LocalTime time;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyGroupId", nullable = false)
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
