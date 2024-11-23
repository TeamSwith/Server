package swith.swithServer.domain.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.comment.entity.Comment;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.jwt.service.JwtTokenProvider;
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
@Tag(name="댓글(comment)")
public class CommentController {

    private final CommentService commentService;
    private final JwtTokenProvider jwtTokenProvider; // JWT 토큰 검증 및 정보 추출
    private final UserRepository userRepository;

    @PostMapping("/{studyId}/{groupId}")
    @Operation(summary = "댓글 생성", description = "현재 로그인된 사용자로 댓글을 생성합니다.")
    public ApiResponse<CommentResponse> createComment(
            @Parameter(description = "ID of the study where the comment will be created", required = true)
            @PathVariable(name = "studyId") Long studyId,
            @Parameter(description = "ID of the group associated with the comment", required = true)
            @PathVariable(name = "groupId") Long groupId,
            @RequestBody CommentRequest request,
            HttpServletRequest httpServletRequest) {

        // 토큰 추출 및 검증
        String token = extractToken(httpServletRequest);
        if (token == null) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }

        String userEmail = jwtTokenProvider.getUserEmail(token);
        if (userEmail == null) {
            throw new BusinessException(ErrorCode.NOT_VALID_ERROR);
        }

        User loginUser = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_DOESNT_EXIST));

        System.out.println("로그인된 사용자: " + loginUser.getEmail());

        // Service에 로그인된 사용자 정보 전달
        return new ApiResponse<>(201, commentService.createComment(studyId, groupId, loginUser, request));
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

//    @PostMapping("/{studyId}/{userId}/{groupId}")
//    @Operation(summary = "댓글 생성", description = "Creates a new comment using studyId, userId, and groupId")
//    public ApiResponse<CommentResponse> createComment(
//            @Parameter(description = "ID of the study where the comment will be created", required = true)
//            @PathVariable(name = "studyId") Long studyId,
//            @Parameter(description = "ID of the user creating the comment", required = true)
//            @PathVariable(name = "userId") Long userId,
//            @Parameter(description = "ID of the group associated with the comment", required = true)
//            @PathVariable(name = "groupId") Long groupId,
//            @RequestBody CommentRequest request) {
//        // PathVariable로 받은 userId와 groupId를 Service에 전달
//        return new ApiResponse<>(201, commentService.createComment(studyId, userId, groupId, request));
//    }

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