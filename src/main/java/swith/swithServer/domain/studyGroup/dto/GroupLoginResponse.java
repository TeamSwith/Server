package swith.swithServer.domain.studyGroup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class GroupLoginResponse {
    private Long groupId;
    private String message;
    private String redirect;

    public static GroupLoginResponse from(Long groupId, String message, String redirect){
        return GroupLoginResponse.builder()
                .groupId(groupId)
                .message(message)
                .redirect(redirect)
                .build();
    }
}
