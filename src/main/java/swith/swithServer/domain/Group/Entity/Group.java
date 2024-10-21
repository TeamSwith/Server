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
    private Long group_id;

    @NotNull
    private String group_pw;

    @NotNull
    private int member_num;

    @NotNull
    private String subject;

    @NotNull
    private String period;

    @NotNull
    private String communication;

    private String notice;

    public void updateMember_num(int member_num){
        this.member_num = member_num;
    }
    public void updateNotice(String notice) {
        this.notice = notice;
    }
}
