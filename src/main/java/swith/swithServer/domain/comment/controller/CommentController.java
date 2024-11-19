package swith.swithServer.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.global.response.ApiResponse;
import swith.swithServer.domain.comment.dto.CommentRequest;
import swith.swithServer.domain.comment.dto.CommentResponse;
import swith.swithServer.domain.comment.dto.CommentUpdateRequest;
import swith.swithServer.domain.comment.service.CommentService;
import swith.swithServer.global.oauth.service.OauthService;

import swith.swithServer.domain.user.entity.User; // getLoginUser 사용하려고 추가함

import io.swagger.v3.oas.annotations.Parameter;
import java.util.List;


@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    private final OauthService oauthService; // OauthService 주입 추가



    // 댓글 생성 API
    @PostMapping("/create")
    @Operation(summary = "Create a new comment", description = "Creates a new comment using the provided request body.")
    public ApiResponse<String> createComment(@RequestBody CommentRequest request) {
        commentService.createComment(request);
//        User loggedInUser = oauthService.getLoginUser(); // OauthService를 통해 로그인된 사용자 정보 가져오기
//        commentService.createComment(request, loggedInUser); // CommentService에 loggedInUser 전달
        return new ApiResponse<>(201, "Comment created successfully");
    }

    // 댓글 삭제 API(commentId)
    @DeleteMapping("/delete")
    @Operation(summary = "Delete a comment", description = "Deletes a comment using its commentId.")
    public ApiResponse<String> deleteComment(
            @RequestParam("commentId") @Parameter(description = "ID of the comment to be deleted") Long commentId) {
//        commentService.deleteComment(commentId);
        User loggedInUser = oauthService.getLoginUser(); // OauthService를 통해 로그인된 사용자 정보 가져오기
        commentService.deleteComment(commentId, loggedInUser); // CommentService에 loggedInUser 전달
        return new ApiResponse<>(200, "Comment deleted successfully");
    }

    // 댓글 조회 API(studyId)
    @GetMapping("/by-study")
    public ApiResponse<List<CommentResponse>> getCommentsByStudyId(
            @RequestParam("studyId") Long studyId) {
//        List<CommentResponse> comments = commentService.getCommentsByStudyId(studyId);
        User loggedInUser = oauthService.getLoginUser(); // OauthService를 통해 로그인된 사용자 정보 가져오기
        List<CommentResponse> comments = commentService.getCommentsByStudyId(studyId, loggedInUser); // CommentService에 loggedInUser 전달
        return new ApiResponse<>(200, comments);
    }

    // 댓글 수정 API
    @PutMapping("/update-content")
    public ApiResponse<String> updateCommentContent(@RequestBody CommentUpdateRequest request) {
//        String updatedContent = commentService.updateCommentContent(request);
        User loggedInUser = oauthService.getLoginUser(); // OauthService를 통해 로그인된 사용자 정보 가져오기
        String updatedContent = commentService.updateCommentContent(request, loggedInUser); // CommentService에 loggedInUser 전달

        return new ApiResponse<>(200, updatedContent);
    }

}
