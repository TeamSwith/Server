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
    EXPIRED_REFRESH_TOKEN(404,"만료된 refreshToken입니다."),

    // Group 관련 에러
    GROUP_INSERT_ID_ALREADY_EXISTS(409, "이미 존재하는 GroupInsertId입니다."),

    // Comment 관련 에러
    COMMENT_DOESNT_EXIST(404, "존재하지 않는 댓글입니다."),

    // UserGroup 관련 에러
    GROUP_DOESNT_EXIST_OR_NO_USERS(404, "해당 그룹이 존재하지 않거나, 그룹에 사용자가 없습니다."),

    // UserTask 관련 에러
    USER_TASK_NOT_FOUND(404, "해당 사용자 ID와 과제 ID로 과제를 찾을 수 없습니다."), //
    TASK_ALREADY_COMPLETED(400, "해당 과제는 이미 완료 상태입니다."),

    INVALID_GROUP_ID(404, "존재하지 않는 그룹 ID입니다."),
    INVALID_STUDY_ID(404, "존재하지 않는 스터디 ID입니다."),
    INVALID_USER_ID(404, "존재하지 않는 사용자 ID입니다.");

    private final int status;
    private final String message;

}
