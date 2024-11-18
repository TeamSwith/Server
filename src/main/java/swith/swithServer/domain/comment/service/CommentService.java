//package swith.swithServer.domain.comment.service;
//
//import lombok.Getter;
//import lombok.RequiredArgsConstructor;
//import lombok.Setter;
//
//import java.util.stream.Collectors;
//import java.util.List;
//
//import org.springframework.stereotype.Service;
//import swith.swithServer.domain.comment.domain.Comment;
//import swith.swithServer.domain.comment.controller.CommentRequestDTO;
//import swith.swithServer.domain.comment.controller.CommentResponseDTO;
//import swith.swithServer.domain.comment.controller.CommentGetResponseDTO;
//import swith.swithServer.domain.comment.repository.CommentRepository;
//import swith.swithServer.domain.group.domain.GroupDomain;
//import swith.swithServer.domain.user.domain.User;
//import swith.swithServer.domain.study.repository.StudyRepository;
//import swith.swithServer.domain.group.repository.GroupRepository;
//import swith.swithServer.domain.user.repository.UserRepository;
//
//@Service
//@Setter
//@Getter
//@RequiredArgsConstructor
//public class CommentService {
//
//    private final CommentRepository commentRepository;
//    private final StudyRepository studyRepository;
//    private final UserRepository userRepository;
//    private final GroupRepository groupRepository;
//
////     // comment 생성
////     public Comment createComment(CommentRequestDTO commentRequest) {
////         // 외래 키 유효성 확인
////         Study study = studyRepository.findById(commentRequest.getStudyId())
////                 .orElseThrow(() -> new IllegalArgumentException("Invalid Study ID"));
////         User user = userRepository.findById(commentRequest.getUserId())
////                 .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));
////         GroupDomain group = groupRepository.findById(commentRequest.getGroupId())
////                 .orElseThrow(() -> new IllegalArgumentException("Invalid Group ID"));
//
////         // Comment 생성
////         Comment comment = new Comment();
////         comment.setContent(commentRequest.getContent());
////         comment.setStudy(study);
////         comment.setUser(user);
////         comment.setGroup(group);
//
////         return commentRepository.save(comment);
////     }
//
//    // comment 삭제
//    public void deleteComment(Long commentId) {
//        // 존재하지 않는 댓글 삭제 시 예외 처리
//        if (!commentRepository.existsById(commentId)) {
//            throw new IllegalArgumentException("Comment with ID " + commentId + " does not exist.");
//        }
//
//        commentRepository.deleteById(commentId);
//    }
//
//    // studyid 에 있는 comment 조회
//    public List<CommentGetResponseDTO> getCommentsByStudyId(Long studyId) {
//        List<Comment> comments = commentRepository.findByStudyId(studyId);
//
//        // Entity -> DTO 변환
//        return comments.stream()
//                .map(comment -> new CommentGetResponseDTO(
//                        comment.getCommentId(),
//                        comment.getContent(),
//                        comment.getUser().getId(),
//                        comment.getStudy().getStudyId(),
//                        comment.getGroup().getGroupId()
//                ))
//                .collect(Collectors.toList());
//    }
//}