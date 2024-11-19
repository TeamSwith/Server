package swith.swithServer.domain.comment.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentUpdateRequest {
    @NotNull
    private Long commentId;

    @NotNull
    private String content;
}