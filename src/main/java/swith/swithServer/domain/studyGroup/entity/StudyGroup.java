package swith.swithServer.domain.studyGroup.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.common.BaseEntity;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
    private int maxNum;

    @NotNull
    private int memberNum;

    @NotNull
    private String subject;

    @NotNull
    private int period;

    @NotNull
    private String communication;

    private String notice;

    public void updateMemberNum(int memberNum){
        this.memberNum = memberNum;
    }
    public void updateNotice(String notice) {
        this.notice = notice;
    }
}