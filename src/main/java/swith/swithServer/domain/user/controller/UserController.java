package swith.swithServer.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.user.service.UserService;
import swith.swithServer.global.response.ApiResponse;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/nickname")
    public ApiResponse<String> getNicknameByUserId(@RequestParam("userId") Long userId) { // 하나여서 dto 말고 param 썼습니다.
        String nickname = userService.getNicknameByUserId(userId);
        return new ApiResponse<>(200, nickname);
    }
}