package swith.swithServer.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swith.swithServer.domain.comment.entity.Comment;
import java.util.List;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByStudyIdOrderByIdAsc(Long studyId);

}