package swith.swithServer.domain.studyGroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Builder;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

@Getter
@Builder
@AllArgsConstructor
public class GroupResponse {
    private String groupId;
    private String groupName;
    private String groupInsertId;
    private Long maxNum;
    private String subject;
    private String period;
    private String communication;

    public static GroupResponse from(StudyGroup studyGroup) {
        return GroupResponse.builder()
                .groupId(String.valueOf(studyGroup.getId()))
                .groupName(studyGroup.getGroupName())
                .groupInsertId(studyGroup.getGroupInsertId())
                .maxNum(studyGroup.getMaxNum())
                .subject(studyGroup.getSubject())
                .period(studyGroup.getPeriod().getLabel())
                .communication(studyGroup.getCommunication())
                .build();
    }

}