package swith.swithServer.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserNicknameResponse {
    private Long userId;
    private String nickname;
}