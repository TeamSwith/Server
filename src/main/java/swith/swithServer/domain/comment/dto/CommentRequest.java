package swith.swithServer.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CommentRequest {

    @NotBlank
    private String content;

    @NotNull
    private Long studyId;

    @NotNull
    private Long userId;

    @NotNull
    private Long groupId;
}