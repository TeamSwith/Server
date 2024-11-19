package swith.swithServer.domain.study.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.group.entity.GroupDomain;
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
    @Column(name = "study_id") // study_id 컬럼과 매핑
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
    private GroupDomain group;


    public Study(LocalDate date, LocalTime time, String location, GroupDomain group){
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


//package swith.swithServer.domain.study.domain;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.NotNull;
//import lombok.*;
//import swith.swithServer.domain.common.BaseEntity;
//import swith.swithServer.domain.group.domain.GroupDomain;
//
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//
//@Getter
//@Entity
//@Table(name = "study")
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Study extends BaseEntity {
//    @Id
//    @GeneratedValue(strategy= GenerationType.IDENTITY)
//    private Long studyId;
//
//    @NotNull
//    private LocalDate date;
//
//    private LocalDateTime time;
//
//    private String location;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", nullable = false)
//    private GroupDomain group;
//
//    public void updateTime(LocalDateTime time) {
//        this.time = time;
//    }
//
//    public void updateLocation(String location){
//        this.location = location;
//    }
//}
