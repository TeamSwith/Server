package swith.swithServer.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.user.entity.User;

@Getter
@AllArgsConstructor
public class UserNicknameImageResponse {

    private String nickname;
    private String image;

    public static UserNicknameImageResponse from(User user) {
        return new UserNicknameImageResponse(user.getNickname(), user.getImage());
    }
}