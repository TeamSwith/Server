package swith.swithServer.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Entity
public class Study {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long study_id;

    @NotNull
    private LocalDate date;

    private LocalTime time;

    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group_id;

}
