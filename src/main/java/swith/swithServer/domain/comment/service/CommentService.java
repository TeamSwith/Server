package swith.swithServer.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.comment.entity.Comment;
import swith.swithServer.domain.comment.dto.CommentRequest;
import swith.swithServer.domain.comment.dto.CommentResponse;
import swith.swithServer.domain.comment.dto.CommentUpdateRequest;
import swith.swithServer.domain.comment.repository.CommentRepository;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.domain.user.repository.UserRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;
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
    @Transactional
    public Comment createComment(Long studyId, CommentRequest request) {
        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_STUDY_ID));

        StudyGroup studyGroup = groupRepository.findById(request.getGroupId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_USER_ID));

        return commentRepository.save(request.toEntity(study, user, studyGroup));
    }

    // 댓글 삭제 API (commentId)
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }

    // 댓글 조회(1개 / 삭제 API에 이용)
    @Transactional(readOnly = true)
    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_DOESNT_EXIST));
    }

    // 스터디 모든 댓글 조회 API (studyId)
    @Transactional(readOnly = true)
    public List<CommentResponse> getCommentsByStudyId(Long studyId) {
        studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_STUDY_ID));

        List<Comment> comments = commentRepository.findByStudyIdOrderByIdAsc(studyId);
        return comments.stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 댓글 수정 API
    @Transactional
    public String updateCommentContent(Long commentId, CommentUpdateRequest request) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_DOESNT_EXIST));

        comment.updateContent(request.getContent());
        commentRepository.save(comment);

        return comment.getContent();
    }
}