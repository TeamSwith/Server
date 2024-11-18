package swith.swithServer.domain.studyGroup.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.common.BaseEntity;


@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StudyGroup extends BaseEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //사용자 입력 id
    @NotNull
    private String groupId;

    @NotNull
    private String groupPw;

    //최대 인원 수
    @NotNull
    private int maxNum;

    //현재 인원 수
    @NotNull
    private int memberNum;

    @NotNull
    private String subject;

    @NotNull
    private String period;

    @NotNull
    private String communication;

    private String notice;

    public void updateMemberNum(int memberNum){
        this.memberNum = memberNum;
    }
    public void updateNotice(String notice) {
        this.notice = notice;
    }
    //테스트용
    public StudyGroup(String groupId, String groupPw, int maxNum, int memberNum, String subject, String period, String communication){
        this.groupId =groupId;
        this.groupPw=groupPw;
        this.maxNum=maxNum;
        this.memberNum=memberNum;
        this.subject=subject;
        this.period=period;
        this.communication=communication;

    }
}
