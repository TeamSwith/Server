package swith.swithServer.domain.usertask.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserTaskUpdateResponse {
    private Long userId;
    private Long taskId;
    private String taskStatus;
}