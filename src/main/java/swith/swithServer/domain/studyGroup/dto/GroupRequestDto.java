package swith.swithServer.domain.studyGroup.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GroupRequestDto {
    private String groupId;
    private String groupPw;
    private Long userId;
}

