package swith.swithServer.domain.Study.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import swith.swithServer.domain.Group.Entity.Group;
import swith.swithServer.domain.common.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Entity
//@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@RequiredArgsConstructor
public class Study extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long studyId;

    @NotNull
    private LocalDate date;

    @Schema(type = "LocalDate")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId", nullable = false)
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
