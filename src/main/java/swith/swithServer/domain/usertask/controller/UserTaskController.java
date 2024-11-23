package swith.swithServer.domain.usertask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.usertask.dto.UpdateTaskStatusRequest;
import swith.swithServer.domain.usertask.service.UserTaskService;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.jwt.service.JwtTokenProvider;
import swith.swithServer.global.response.ApiResponse;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.repository.UserRepository;

@RestController
@RequestMapping("/usertasks")
@RequiredArgsConstructor
@Tag(name = "User 과제")
public class UserTaskController {

    private final UserTaskService userTaskService;
    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 검증 및 정보 추출
    private final UserRepository userRepository;

    @PutMapping("/{taskId}")
    @Operation(summary = "과제 상태 업데이트", description = "현재 로그인된 사용자와 taskId를 사용하여 과제 상태를 업데이트합니다.")
    public ApiResponse<String> updateTaskStatus(
            @Parameter(description = "ID of the task", required = true)
            @PathVariable(name = "taskId") Long taskId,
            @RequestBody UpdateTaskStatusRequest request,
            HttpServletRequest httpServletRequest) {

        String token = extractToken(httpServletRequest);
        if (token == null) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }

        String userEmail = jwtTokenProvider.getUserEmail(token);
        if (userEmail == null) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }

        User loginUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));

        System.out.println("로그인된 사용자: " + loginUser.getEmail());

        String updatedStatus = userTaskService.updateTaskStatus(loginUser.getId(), taskId, request.getTaskStatus());
        return new ApiResponse<>(200, "Task status updated to " + updatedStatus);
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}