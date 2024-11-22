package swith.swithServer.domain.study.dto;

import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.study.entity.Study;

@Getter
@Builder
public class MessageResponse {
    private String message;

    public static MessageResponse from(String message){
        return MessageResponse.builder()
                .message(message)
                .build();
    }

    public static MessageResponse from(){
        return MessageResponse.builder()
                .message("삭제되었습니다.")
                .build();
    }
}
