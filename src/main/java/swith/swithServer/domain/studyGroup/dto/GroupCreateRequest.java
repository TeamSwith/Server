package swith.swithServer.domain.studyGroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import swith.swithServer.domain.studyGroup.entity.Period;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GroupCreateRequest {
    private final String groupInsertId;
    private final String groupPw;
    private final String groupName;
    private final Long maxNum;
    private final Long memberNum;
    private final String subject;
    private final String period; // 변경된 부분
    private final String communication;

    public static GroupCreateRequest from(String groupInsertId, String groupPw, String groupName, Long maxNum, Long memberNum, String subject, String period, String communication) {
        return GroupCreateRequest.builder()
                .groupInsertId(groupInsertId)
                .groupPw(groupPw)
                .groupName(groupName)
                .maxNum(maxNum)
                .memberNum(memberNum)
                .subject(subject)
                .period(period)
                .communication(communication)
                .build();
    }

    public StudyGroup toEntity() {
        return StudyGroup.builder()
                .groupInsertId(this.groupInsertId)
                .groupPw(this.groupPw)
                .groupName(this.groupName != null ? this.groupName : "")
                .maxNum(this.maxNum != null ? this.maxNum : 0L)
                .memberNum(this.memberNum != null ? this.memberNum : 0L)
                .subject(this.subject != null ? this.subject : "")
                .period(Period.fromLabel(this.period))
                .communication(this.communication != null ? this.communication : "")
                .build();
//                .groupInsertId(this.groupInsertId)
//                .groupPw(this.groupPw)
//                .groupName(this.groupName)
//                .maxNum(this.maxNum)
//                .memberNum(this.memberNum)
//                .subject(this.subject)
//                .period(Period.fromLabel(this.period))
//                .communication(this.communication)
//                .build();
    }
}