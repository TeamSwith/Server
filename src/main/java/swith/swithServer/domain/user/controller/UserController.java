package swith.swithServer.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.user.service.UserService;
import swith.swithServer.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // userId로 닉네임 가져오기 API
    @GetMapping("/{userId}")
    @Operation(summary = "Get user nickname", description = "Using userId.")
    public ApiResponse<String> getNicknameByUserId(
            @Parameter(description = "ID of the user to fetch the nickname", required = true)
            @PathVariable(name = "userId") Long userId) {
        String nickname = userService.getNicknameByUserId(userId);
        return new ApiResponse<>(200, nickname);
    }
}