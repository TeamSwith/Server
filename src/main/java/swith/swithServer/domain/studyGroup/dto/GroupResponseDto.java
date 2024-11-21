package swith.swithServer.domain.studyGroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.study.dto.StudyResponseDto;
import swith.swithServer.domain.study.entity.Study;

@Getter
@Builder
@AllArgsConstructor
public class GroupResponseDto {
    private Long groupId;
    private String message;
    private String redirect;

    public static GroupResponseDto from(Long groupId, String message, String redirect){
        return GroupResponseDto.builder()
                .groupId(groupId)
                .message(message)
                .redirect(redirect)
                .build();
    }
}
