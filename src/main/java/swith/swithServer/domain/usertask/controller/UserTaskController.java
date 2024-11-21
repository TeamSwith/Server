package swith.swithServer.domain.usertask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.usertask.dto.UpdateTaskStatusRequest;
import swith.swithServer.domain.usertask.service.UserTaskService;
import swith.swithServer.global.response.ApiResponse;

@RestController
@RequestMapping("/api/usertasks")
@RequiredArgsConstructor
public class UserTaskController {

    private final UserTaskService userTaskService;

    // Task 상태 COMPLETED로 업데이트 API
    @PutMapping("/{userId}/{taskId}/COMPLETED")
    @Operation(summary = "Update task status to COMPLETED", description = "Using userId, taskId")
    public ApiResponse<String> updateTaskStatus(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable(name = "userId") Long userId,
            @Parameter(description = "ID of the task", required = true)
            @PathVariable(name = "taskId") Long taskId) {
        String updatedStatus = userTaskService.updateTaskStatus(userId, taskId);
        return new ApiResponse<>(200, "Task status updated to " + updatedStatus);
    }

    // Task 상태 PENDING으로 업데이트 API
    @PutMapping("/{userId}/{taskId}/PENDING")
    @Operation(summary = "Update task status to PENDING", description = "Using userId, taskId")
    public ApiResponse<String> updateTaskStatusToPending(
            @Parameter(description = "ID of the user", required = true)
            @PathVariable(name = "userId") Long userId,
            @Parameter(description = "ID of the task", required = true)
            @PathVariable(name = "taskId") Long taskId) {
        String updatedStatus = userTaskService.updateTaskStatusToPending(userId, taskId);
        return new ApiResponse<>(200, "Task status updated to " + updatedStatus);
    }
}