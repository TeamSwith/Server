package swith.swithServer.global.oauth.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.global.jwt.dto.JwtTokenDto;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenResponseDto {

    private JwtTokenDto tokens;

    public static TokenResponseDto from(JwtTokenDto tokens) {
        return TokenResponseDto.builder()
                .tokens(tokens)
                .build();
    }
}
