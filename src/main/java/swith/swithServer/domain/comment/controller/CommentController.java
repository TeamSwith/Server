package swith.swithServer.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.comment.entity.Comment;
import swith.swithServer.global.response.ApiResponse;
import swith.swithServer.domain.comment.dto.CommentRequest;
import swith.swithServer.domain.comment.dto.CommentResponse;
import swith.swithServer.domain.comment.dto.CommentUpdateRequest;
import swith.swithServer.domain.comment.service.CommentService;

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name="댓글(comment)")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성 API
    @PostMapping("/{studyId}/{userId}/{groupId}")
    @Operation(summary = "댓글 생성", description = "Creates a new comment using studyId, userId, and groupId")
    public ApiResponse<CommentResponse> createComment(
            @Parameter(description = "ID of the study where the comment will be created", required = true)
            @PathVariable(name = "studyId") Long studyId,
            @Parameter(description = "ID of the user creating the comment", required = true)
            @PathVariable(name = "groupId") Long groupId,
            @RequestBody CommentRequest request) {
        return new ApiResponse<>(201, commentService.createComment(studyId, groupId, request));
    }

    // 댓글 삭제 API
    @DeleteMapping("/{commentId}")
    @Operation(summary = "댓글 삭제", description = "Deletes comment using commentId.")
    public ApiResponse<CommentResponse> deleteComment(
            @Parameter(description = "ID of the comment to be deleted", required = true)
            @PathVariable(name = "commentId") Long commentId) {
        Comment deletedComment = commentService.getCommentById(commentId); // 삭제 전 조회
        commentService.deleteComment(commentId); // 실제 삭제
        return new ApiResponse<>(200, CommentResponse.fromEntity(deletedComment)); // 삭제된 데이터 반환

    }

    // 댓글 조회 API
    @GetMapping("/{studyId}")
    @Operation(summary = "댓글 조회", description = "Get comments using commentid")
    public ApiResponse<List<CommentResponse>> getCommentsByStudyId(
            @Parameter(description = "ID of the study to fetch comments for", required = true)
            @PathVariable(name = "studyId") Long studyId) {
        List<CommentResponse> comments = commentService.getCommentsByStudyId(studyId);
        return new ApiResponse<>(200, comments);
    }

    // 댓글 수정 API
    @PutMapping("/{commentId}")
    @Operation(summary = "댓글 수정", description = "Updates the content of a comment using its commentId.")
    public ApiResponse<String> updateCommentContent(
            @Parameter(description = "ID of the comment to be updated", required = true)
            @PathVariable(name = "commentId") Long commentId,
            @RequestBody CommentUpdateRequest request) {
        String updatedContent = commentService.updateCommentContent(commentId, request);
        return new ApiResponse<>(200, updatedContent);
    }
}