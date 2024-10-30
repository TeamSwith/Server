package swith.swithServer.domain.task;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swith.swithServer.domain.common.BaseEntity;
// 스터디 참조 코드 추가 예정

// 논의 사항 : ERD에 과제 상태, 시간 추가해야하지 않을까요?

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @NotNull
    @Column(length = 50)
    private String content;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "studyId", nullable = false)
//    private Study study;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
}