package swith.swithServer.domain.studyGroup.dto;

import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

@Getter
@Builder
public class GroupNoticeResponse {
    private Long id;
    private String notice;

    public static GroupNoticeResponse from(StudyGroup studyGroup){
        return GroupNoticeResponse.builder()
                .id(studyGroup.getId())
                .notice(studyGroup.getNotice())
                .build();
    }

}
