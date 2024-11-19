package swith.swithServer.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.comment.entity.Comment;
import swith.swithServer.domain.comment.dto.CommentRequest;
import swith.swithServer.domain.comment.dto.CommentResponse;
import swith.swithServer.domain.comment.dto.CommentUpdateRequest;
import swith.swithServer.domain.comment.repository.CommentRepository;
import swith.swithServer.domain.group.entity.GroupDomain;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.group.repository.GroupRepository;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.domain.user.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final StudyRepository studyRepository;
    private final UserRepository userRepository;
    private final GroupRepository groupRepository;

    // 댓글 생성 API
    public void createComment(CommentRequest request) {
        GroupDomain group = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new IllegalArgumentException("Group not found with ID: " + request.getGroupId()));

        Study study = studyRepository.findById(request.getStudyId())
                .orElseThrow(() -> new IllegalArgumentException("Study not found with ID: " + request.getStudyId()));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        Comment comment = new Comment(
                request.getContent(),
                study,
                user,
                group
        );

        commentRepository.save(comment);
    }

    // 댓글 삭제 API(commentId)
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("Comment not found with ID: " + commentId));
        commentRepository.delete(comment);
    }

    // 댓글 조회 API(studyId)
    public List<CommentResponse> getCommentsByStudyId(Long studyId) {
        List<Comment> comments = commentRepository.findByStudyIdOrderByIdAsc(studyId);
        return comments.stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 댓글 수정 API
    public String updateCommentContent(CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(request.getCommentId())
                .orElseThrow(() -> new IllegalArgumentException("Comment not found"));

        comment.updateContent(request.getContent());
        commentRepository.save(comment);
        return comment.getContent();
    }

}