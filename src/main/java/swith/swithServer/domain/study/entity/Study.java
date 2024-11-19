package swith.swithServer.domain.study.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.group.entity.Group;
import swith.swithServer.domain.common.BaseEntity;

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
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;


    public Study(LocalDate date, LocalTime time, String location, Group group){
        this.date = date;
        this.time = time;
        this.location = location;
        this.group = group;
    }
    public void updateTime(LocalTime time) {
        this.time = time;
    }

    public void updateLocation(String location){
        this.location = location;
    }
}