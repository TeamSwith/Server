package swith.swithServer.domain.usertask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.usertask.dto.UpdateTaskStatusRequest;
import swith.swithServer.domain.usertask.service.UserTaskService;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.global.response.ApiResponse;

@RestController
@RequestMapping("/usertasks")
@RequiredArgsConstructor
@Tag(name = "User 과제")
public class UserTaskController {

    private final UserTaskService userTaskService;

    @PutMapping("/{taskId}")
    @Operation(summary = "과제 상태 업데이트", description = "현재 로그인된 사용자와 taskId를 사용하여 과제 상태를 업데이트합니다.")
    public ApiResponse<String> updateTaskStatus(
            @Parameter(description = "ID of the task", required = true)
            @PathVariable(name = "taskId") Long taskId,
            @RequestBody UpdateTaskStatusRequest request) {
        String updatedStatus = userTaskService.updateTaskStatus(taskId, request.getTaskStatus());
        return new ApiResponse<>(200, "Task status updated to " + updatedStatus);
    }

}