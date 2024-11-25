package swith.swithServer.domain.studyGroup.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.common.BaseEntity;
import swith.swithServer.domain.study.entity.Study;

import java.util.ArrayList;
import java.util.List;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Table(name = "`group`")
public class StudyGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "studyGroupId")
    private Long id;

    // 사용자 입력 ID
    @NotNull
    private String groupInsertId;

    @NotNull
    private String groupPw;

    @NotNull
    private String groupName;

    @NotNull
    private Long maxNum;

    @NotNull
    private Long memberNum;

    @NotNull
    private String subject;


    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "period", length = 20)
    private Period period;

    @NotNull
    private String communication;

    private String notice;

    @OneToMany(mappedBy = "studyGroup", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @Builder.Default
    private List<Study> studies = new ArrayList<>();

    public void updateMemberNum(Long memberNum){
        this.memberNum = memberNum;
    }
    public void updateNotice(String notice) {
        this.notice = notice;
    }

    public void updateGroupDetails(String groupName, Long maxNum, String subject, Period period, String communication) {
        this.groupName = groupName;
        this.maxNum = maxNum;
        this.subject = subject;
        this.period = period;
        this.communication = communication;
    }
}