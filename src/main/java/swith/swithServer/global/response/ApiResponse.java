package swith.swithServer.global.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiResponse<T> {

    private final boolean success = true;

    private final int code;

    private final T data;

}
