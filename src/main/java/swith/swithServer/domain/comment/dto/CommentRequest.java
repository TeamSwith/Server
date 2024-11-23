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

    public Comment toEntity(Study study, User user, StudyGroup studyGroup) {
        return new Comment(content, study, user, studyGroup);
    }
}