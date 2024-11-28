package swith.swithServer.domain.studyGroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

@Getter
@AllArgsConstructor
public class GroupIdAndInsertIdResponse {
    private Long groupId;
    private String groupInsertId;

    public static GroupIdAndInsertIdResponse from(StudyGroup studyGroup) {
        return new GroupIdAndInsertIdResponse(
                studyGroup.getId(),
                studyGroup.getGroupInsertId()
        );
    }
}