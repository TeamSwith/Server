//package swith.swithServer.domain.comment.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import java.util.List;
//import swith.swithServer.domain.comment.domain.Comment;
//import swith.swithServer.domain.comment.service.CommentService;
//import swith.swithServer.domain.comment.controller.CommentRequestDTO;
//import swith.swithServer.domain.comment.controller.CommentResponseDTO;
//
//@RestController
//@RequestMapping("/api/comments")
//@RequiredArgsConstructor
//public class CommentController {
//
//    private final CommentService commentService;
//
//    // comment 작성
//    // @PostMapping
//    // public ResponseEntity<CommentResponseDTO> createComment(@RequestBody CommentRequestDTO commentRequest) {
//    //     Comment comment = commentService.createComment(commentRequest);
//    //     CommentResponseDTO response = new CommentResponseDTO(comment.getCommentId(), comment.getContent());
//    //     return new ResponseEntity<>(response, HttpStatus.CREATED);
//    // }
//
//    // comment 삭제
//    @DeleteMapping("/{commentId}")
//    public ResponseEntity<String> deleteComment(@PathVariable Long commentId) {
//        commentService.deleteComment(commentId);
//        return new ResponseEntity<>("Comment deleted successfully", HttpStatus.OK);
//    }
//
//    // comment 조회
//    @GetMapping("/study/{studyId}")
//    public ResponseEntity<List<CommentResponseDTO>> getCommentsByStudyId(@PathVariable Long studyId) {
//        List<CommentResponseDTO> comments = commentService.getCommentsByStudyId(studyId);
//        return ResponseEntity.ok(comments);
//    }
//}