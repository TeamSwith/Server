package swith.swithServer.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.comment.dto.CommentResponseWithStudyId;
import swith.swithServer.domain.comment.entity.Comment;
import swith.swithServer.domain.comment.dto.CommentRequest;
import swith.swithServer.domain.comment.dto.CommentResponse;
import swith.swithServer.domain.comment.dto.CommentUpdateRequest;
import swith.swithServer.domain.comment.repository.CommentRepository;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.global.oauth.service.OauthService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final StudyRepository studyRepository;
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final OauthService authService;

    // 댓글 생성 API

    @Transactional
    public CommentResponse createComment(Long studyId, Long groupId, CommentRequest request) {
        User user = authService.getLoginUser();

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_STUDY_ID));

        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        boolean isUserInGroup = userGroupRepository.existsByUserAndStudyGroup(user, studyGroup);
        if (!isUserInGroup) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }

        Comment comment = request.toEntity(study, user, studyGroup);

        return CommentResponse.fromEntity(commentRepository.save(comment));
    }

    // 댓글 삭제 API (commentId)
    @Transactional
    public void deleteComment(Long commentId) {
        User user = authService.getLoginUser();
        Comment comment = getCommentById(commentId);

        if (!userGroupRepository.existsByUserAndStudyGroup(user, comment.getStudyGroup())) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }

        commentRepository.delete(comment);
    }

    // 댓글 조회(1개 / 삭제 API에 이용)
    @Transactional(readOnly = true)
    public Comment getCommentById(Long commentId) {
        User user = authService.getLoginUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_DOESNT_EXIST));

        if (!userGroupRepository.existsByUserAndStudyGroup(user, comment.getStudyGroup())) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }

        return comment;
    }

    // 스터디 모든 댓글 조회 API (studyId)
    @Transactional(readOnly = true)
    public CommentResponseWithStudyId getCommentsByStudyIdWithStudyId(Long studyId) {
        User user = authService.getLoginUser();

        Study study = studyRepository.findById(studyId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_STUDY_ID));

        if (!userGroupRepository.existsByUserAndStudyGroup(user, study.getStudyGroup())) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }

        List<Comment> comments = commentRepository.findByStudyIdOrderByIdAsc(studyId);

        if (comments.isEmpty()) {
            throw new BusinessException(ErrorCode.NO_COMMENTS_FOUND);
        }

        return CommentResponseWithStudyId.fromEntity(studyId, comments);
    }

    // 댓글 수정 API
    @Transactional
    public CommentResponse updateCommentContent(Long commentId, CommentUpdateRequest request) {
        User user = authService.getLoginUser();

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.COMMENT_DOESNT_EXIST));
        if (!userGroupRepository.existsByUserAndStudyGroup(user, comment.getStudyGroup())) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }

        comment.updateContent(request.getContent());
        commentRepository.save(comment);

        return CommentResponse.fromEntity(comment);
    }
}