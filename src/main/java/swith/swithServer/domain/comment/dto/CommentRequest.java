package swith.swithServer.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import swith.swithServer.domain.comment.entity.Comment;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.user.entity.User;


@Getter
public class CommentRequest {

    @NotBlank
    private String content;

    @NotNull
    private Long userId;

    @NotNull
    private Long groupId;

    // Comment 엔티티를 생성하는 메서드 추가
    public Comment toEntity(Study study, User user, StudyGroup studyGroup) {
        return new Comment(content, study, user, studyGroup);
    }
}