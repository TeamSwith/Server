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
    GROUP_HAS_NO_USERS(404, "그룹에 사용자가 없습니다."),

    // UserTask 관련 에러
    USER_TASK_NOT_FOUND(404, "해당 사용자 ID와 과제 ID로 과제를 찾을 수 없습니다."), //
    TASK_ALREADY_COMPLETED(400, "해당 과제는 이미 완료 상태입니다."),
    TASK_NOT_COMPLETED(400, "해당 과제는 완료 상태가 아닙니다."), // 추가된 에러 코드


    INVALID_GROUP_ID(404, "존재하지 않는 그룹 ID입니다."),
    INVALID_STUDY_ID(404, "존재하지 않는 스터디 ID입니다."),
    INVALID_USER_ID(404, "존재하지 않는 사용자 ID입니다."),


    //GROUP
    GROUP_DOESNT_EXIST(404,"존재하지 않는 그룹입니다."),

    GROUP_LOGIN_ERROR(404, "아이디와 비밀번호가 일치하지 않습니다."),

    ALREADY_JOIN(404, "이미 가입한 그룹입니다."),

    //STUDY
    STUDY_DOESNT_EXIST(404,"존재하지 않는 스터디 일정입니다."),

    STUDY_EXIST(404, "이미 존재하는 스터디 일정입니다."),

    //Task
    TASK_DOESNT_EXIST(404, "존재하지 않는 과제입니다");

    private final int status;
    private final String message;

}
