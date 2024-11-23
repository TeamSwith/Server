package swith.swithServer.domain.studyGroup.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.studyGroup.dto.GroupRequest;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.dto.GroupCreateRequest;
import swith.swithServer.domain.studyGroup.dto.GroupUpdateRequest;
import swith.swithServer.domain.studyGroup.dto.GroupResponse;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class GroupService {

    @Autowired
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;


    //id로 찾기
    public StudyGroup getGroupById(Long id) {
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));
        return studyGroup;
    }

    //groupInsertId,groupPw에 매칭되는 그룹 찾기
    public StudyGroup getGroupByInsertIdPw(GroupRequest groupRequest) {
        StudyGroup studyGroup = groupRepository.findByGroupInsertIdAndGroupPw(groupRequest.getGroupInsertId(), groupRequest.getGroupPw())
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_LOGIN_ERROR));
        return studyGroup;
    }

    //사용자의 스터디 가입 여부 확인
    public boolean isUserInGroup(User user, StudyGroup studyGroup) {
        return userGroupRepository.existsByUserAndStudyGroup(user, studyGroup);
    }

    //notice 수정
    @Transactional
    public StudyGroup updateNotice(Long id, String notice) {
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));

        studyGroup.updateNotice(notice);
        return groupRepository.save(studyGroup);
    }

    // 스터디 생성
    @Transactional
    public StudyGroup createGroup(GroupCreateRequest request) {
        if (groupRepository.existsByGroupInsertId(request.getGroupInsertId())) {
            throw new BusinessException(ErrorCode.GROUP_INSERT_ID_ALREADY_EXISTS);
        }
        StudyGroup studyGroup = request.toEntity();
        return groupRepository.save(studyGroup);
    }
    public String findGroupInsertIdByGroupId(Long groupId) {
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));
        return studyGroup.getGroupInsertId();
    }

    // 그룹 정보 수정
    @Transactional
    public GroupResponse updateGroupAndGetDetails(Long groupId, GroupUpdateRequest updateRequest) {
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        updateRequest.applyTo(studyGroup);
        groupRepository.save(studyGroup);

        return GroupResponse.from(studyGroup);
    }

    // groupId로 그룹정보 GET
    public GroupResponse getGroupDetails(Long groupId) {
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        return GroupResponse.from(studyGroup);
    }

    // groupId로 그룹 삭제
    @Transactional
    public void deleteGroup(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new BusinessException(ErrorCode.INVALID_GROUP_ID);
        }
        groupRepository.deleteById(groupId);
    }
}