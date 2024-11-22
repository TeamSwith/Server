package swith.swithServer.global.oauth.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Builder(access = AccessLevel.PRIVATE)
@Getter
public class KakaoUserDto {

    private Long id;        // 카카오 고유 ID
    private String email;     // 사용자 이메일
    private String nickname;  // 사용자 닉네임
    private String image; // 사용자 사진

    public static KakaoUserDto of(Long id, String email, String nickname,String image){
        return KakaoUserDto.builder()
                .id(id)
                .email(email)
                .nickname(nickname)
                .image(image)
                .build();
    }
}
