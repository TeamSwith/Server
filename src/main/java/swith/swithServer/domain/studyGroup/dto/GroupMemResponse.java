package swith.swithServer.domain.studyGroup.dto;

import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

@Getter
@Builder
public class GroupMemResponse {
    private Long id;
    private Long mem;

    public static GroupMemResponse from(StudyGroup studyGroup){
        return GroupMemResponse.builder()
                .id(studyGroup.getId())
                .mem(studyGroup.getMemberNum())
                .build();
    }
}
