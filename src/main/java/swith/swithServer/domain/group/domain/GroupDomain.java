package swith.swithServer.domain.group.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import swith.swithServer.domain.common.BaseEntity;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class GroupDomain extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

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

//    public void updateMemberNum(int memberNum){
//        this.memberNum = memberNum;
//    }
//    public void updateNotice(String notice) {
//        this.notice = notice;
//    }

}
