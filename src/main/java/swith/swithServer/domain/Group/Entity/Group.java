package swith.swithServer.domain.Group.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import swith.swithServer.domain.common.BaseEntity;


@Getter
@Entity
@RequiredArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long groupId;

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
}
