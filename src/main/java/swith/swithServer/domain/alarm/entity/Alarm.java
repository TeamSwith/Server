package swith.swithServer.domain.alarm.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.common.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class Alarm extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @Builder.Default
    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserAlarm> userAlarms = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GroupAlarm> groupAlarms = new ArrayList<>();
//    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<UserAlarm> userAlarms = new ArrayList<>();
//
//    @OneToMany(mappedBy = "alarm", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<GroupAlarm> groupAlarms = new ArrayList<>();
}

