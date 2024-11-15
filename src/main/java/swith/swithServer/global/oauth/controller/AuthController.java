package swith.swithServer.global.oauth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.jwt.dto.JwtTokenDto;
import swith.swithServer.global.jwt.service.JwtTokenProvider;
import swith.swithServer.global.oauth.dto.KakaoUserDto;
import swith.swithServer.global.oauth.dto.TokenResponseDto;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.global.response.ApiResponse;

@RestController
@RequiredArgsConstructor
@Tag(name="카카오 로그인")
public class AuthController {

    private final OauthService authService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @GetMapping("/oauth/kakao/login")
    @Operation(summary = "카카오 로그인 - 토큰 발급")
    public ApiResponse<TokenResponseDto> kakaoCallback(@RequestParam String code) {

        String kakaoAccessToken = authService.getKakaoAccessToken(code);

        KakaoUserDto kakaoUser = authService.getKakaoUserInfo(kakaoAccessToken);

        JwtTokenDto jwtTokens;
        if (authService.isUserExists(kakaoUser)) {
            jwtTokens = authService.loginUser(kakaoUser);
        } else {
            jwtTokens = authService.SignupUser(kakaoUser);
        }

        return new ApiResponse<>(200,TokenResponseDto.from(jwtTokens));
    }

    @PostMapping("/oauth/kakao/refresh")
    @Operation(summary = "refresh 토큰 이용해 토큰 재발급")
    public ApiResponse<TokenResponseDto> reissueAccessToken(String refreshToken) {

//        User user=authService.getLoginUser();
//
//        if (!jwtTokenProvider.validateToken(refreshToken)) {  // refreshToken 도 만료된 경우
//            throw new BusinessException(ErrorCode.EXPIRED_REFRESH_TOKEN);
//        }

        Long userId = Long.parseLong( jwtTokenProvider.getUserEmail(refreshToken));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));

        JwtTokenDto newTokens = jwtTokenProvider.createTokens(user.getEmail());
        return new ApiResponse<>(200,TokenResponseDto.from(newTokens));
    }

    @GetMapping("/getLoginUser")
    @Operation(summary = "현재 로그인되어있는 유저 [test용]")
    public ApiResponse<String> getLoginUser(){
        User user=authService.getLoginUser();
        return new ApiResponse<>(200,user.getEmail());
    }



}
