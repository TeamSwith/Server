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

    private String groupInsertId;
    private String groupPw;

    public StudyGroup toEntity() {
        return StudyGroup.builder()
                .groupInsertId(this.groupInsertId)
                .groupPw(this.groupPw)
                .groupName("")
                .maxNum(0L)
                .memberNum(0L)
                .subject("")
                .period(Period.NONE)
                .communication("")
                .notice("")
                .build();
    }
}