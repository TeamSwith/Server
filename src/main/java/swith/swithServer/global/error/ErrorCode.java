package swith.swithServer.global.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // USER
    USER_DOESNT_EXIST(404,"존재하지 않는 회원입니다."),

    //NOT_VALID
    NOT_VALID_ERROR(404,"잘못된 파라미터입니다."),

    //TOKEN
    EXPIRED_REFRESH_TOKEN(404,"만료된 refreshToken입니다.");


    private final int status;
    private final String message;

}
