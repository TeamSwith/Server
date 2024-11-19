package swith.swithServer.domain.usertask.controller;

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

    // Task 상태 업데이트 API
    @PutMapping("/update-status")
    public ApiResponse<String> updateTaskStatus(@RequestBody UpdateTaskStatusRequest request) {
        String updatedStatus = userTaskService.updateTaskStatus(request.getUserId(), request.getTaskId());
        return new ApiResponse<>(200, "Task status updated to " + updatedStatus);
    }
}