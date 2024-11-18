//package swith.swithServer.domain.comment.repository;
//import java.util.List; // List 임포트
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//import swith.swithServer.domain.comment.domain.Comment;
//
//public interface CommentRepository extends JpaRepository<Comment, Long> {
//    // 특정 스터디 ID에 속한 댓글 조회
//    List<Comment> findByStudyId(Long studyId);
//}