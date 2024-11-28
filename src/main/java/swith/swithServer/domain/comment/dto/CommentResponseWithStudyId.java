package swith.swithServer.domain.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import swith.swithServer.domain.comment.entity.Comment;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class CommentResponseWithStudyId {
    private Long studyId; // 조회한 스터디 ID
    private List<CommentResponse> comments; // 댓글 리스트

    // Entity 리스트와 studyId를 기반으로 DTO 생성
    public static CommentResponseWithStudyId fromEntity(Long studyId, List<Comment> comments) {
        List<CommentResponse> commentResponses = comments.stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
        return new CommentResponseWithStudyId(studyId, commentResponses);
    }
}