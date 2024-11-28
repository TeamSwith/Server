package swith.swithServer.domain.studyGroup.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.domain.studyGroup.dto.*;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.userGroup.entity.UserGroup;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.domain.study.repository.StudyRepository;
import swith.swithServer.domain.study.entity.Study;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupService {

    @Autowired
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;
    private final OauthService authService;
    private final StudyRepository studyRepository;
    private final StudyService studyService;


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
    public StudyGroup updateNotice(Long id, StringRequest stringRequest) {
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));
        studyGroup.updateNotice(stringRequest.getMessage());
        return groupRepository.save(studyGroup);
    }

    // 스터디 생성
    @Transactional
    public StudyGroup createGroup(GroupCreateRequest request) {
        User user = authService.getLoginUser();
        if (groupRepository.existsByGroupInsertId(request.getGroupInsertId())) {
            throw new BusinessException(ErrorCode.GROUP_INSERT_ID_ALREADY_EXISTS);
        }
        StudyGroup studyGroup = request.toEntity();
        return groupRepository.save(studyGroup);
    }

    // 그룹아이디로 스터디 아이디 가져오기
    public String findGroupInsertIdByGroupId(Long groupId) {
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));
        return studyGroup.getGroupInsertId();
    }

    // 그룹 정보 수정
    @Transactional
    public GroupResponse updateGroupAndGetDetails(Long groupId, GroupUpdateRequest updateRequest) {
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        updateRequest.applyTo(studyGroup);
        groupRepository.save(studyGroup);

        return GroupResponse.from(studyGroup);
    }

    // groupId로 그룹정보 GET
    public GroupResponse getGroupDetails(Long groupId) {
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        return GroupResponse.from(studyGroup);
    }

    // groupId로 그룹 삭제
    @Transactional
    public GroupResponse deleteGroup(Long groupId) {
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        List<UserGroup> userGroups = userGroupRepository.findAllByStudyGroup(studyGroup);
        userGroupRepository.deleteAll(userGroups);

        List<Study> studies = studyRepository.findAllByStudyGroup(studyGroup);
        for(Study study : studies){
            studyService.deleteStudy(study.getId());
        }
        GroupResponse groupResponse = GroupResponse.from(studyGroup);

        groupRepository.deleteById(groupId);
        return groupResponse;
    }

}