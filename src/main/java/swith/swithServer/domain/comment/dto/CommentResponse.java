package swith.swithServer.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.comment.entity.Comment;

@Getter
@AllArgsConstructor
public class CommentResponse {
    private Long commentId;
    private String content;
    private Long userId;
    private Long groupId;

    public static CommentResponse fromEntity(Comment comment) {
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getStudyGroup().getId()
        );
    }
}