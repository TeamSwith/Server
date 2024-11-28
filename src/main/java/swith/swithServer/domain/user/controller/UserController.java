package swith.swithServer.domain.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.user.dto.UserNicknameResponse;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.service.UserService;
import swith.swithServer.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import swith.swithServer.global.oauth.service.OauthService;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name="User")
public class UserController {

    private final UserService userService;
    private final OauthService oauthService;

    // 로그인된 사용자 닉네임 가져오기 API
    @GetMapping("/me/nickname")
    @Operation(summary = "로그인된 사용자의 닉네임 조회", description = "Fetch nickname of the currently logged-in user.")
    public ApiResponse<UserNicknameResponse> getNicknameOfLoginUser() {
        User loginUser = userService.getLoginUser(); // 로그인된 사용자 정보 가져오기
        return new ApiResponse<>(200, new UserNicknameResponse(loginUser.getId(), loginUser.getNickname()));
    }

}