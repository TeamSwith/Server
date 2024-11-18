package swith.swithServer.domain.study.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
//import swith.swithServer.domain.group.domain.GroupDomain;
import swith.swithServer.domain.common.BaseEntity;


import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyDomain extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long studyId;

    @NotNull
    private LocalDate date;

    private LocalDateTime time;

    private String location;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "group_id", nullable = false)
//    private GroupDomain group;

    public void updateTime(LocalDateTime time) {
        this.time = time;
    }

    public void updateLocation(String location){
        this.location = location;
    }
}
