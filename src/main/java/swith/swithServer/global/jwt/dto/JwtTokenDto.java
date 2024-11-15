package swith.swithServer.global.jwt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class JwtTokenDto {

    private final String email;

    private final String accessToken;

    private final String refreshToken;

    public static JwtTokenDto of(String email,String accessToken,String refreshToken){
        return JwtTokenDto.builder()
                .email(email)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
