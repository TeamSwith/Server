package swith.swithServer.domain.study.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.common.BaseEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Study extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private LocalDate date;

    private LocalDateTime time;

    private String location;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "groupId", nullable = false)
//    private Group group;

    public void updateTime(LocalDateTime time) {
        this.time = time;
    }

    public void updateLocation(String location){
        this.location = location;
    }
}
