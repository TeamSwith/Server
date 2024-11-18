package swith.swithServer.domain.task.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swith.swithServer.domain.common.BaseEntity;
import swith.swithServer.domain.study.entity.Study;
// 스터디 참조 코드 추가 예정

// 논의 사항 : ERD에 과제 상태, 시간 추가해야하지 않을까요?

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Task extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 50)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "studyId", nullable = false)
    private Study study;

//    @NotNull
//    @Enumerated(EnumType.STRING)
//    private TaskStatus status;

    public Task(String content, Study study){
        this.content = content;
        this.study = study;
    }
}