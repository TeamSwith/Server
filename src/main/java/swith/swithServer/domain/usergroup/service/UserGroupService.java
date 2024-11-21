package swith.swithServer.domain.usergroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.usergroup.entity.UserGroup;
import swith.swithServer.domain.usergroup.repository.UserGroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.domain.group.repository.GroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;
    private final GroupRepository groupRepository;


    // groupId로 사용자 ID 목록 조회 API
    // groupId로 사용자 ID 목록 조회 API
    public List<Long> findUserIdsByGroupId(Long groupId) {
        // group 존재 여부 확인
        boolean groupExists = groupRepository.existsById(groupId);
        if (!groupExists) {
            throw new BusinessException(ErrorCode.INVALID_GROUP_ID);
        }

        // 사용자 목록 조회
        List<UserGroup> userGroups = userGroupRepository.findByStudyGroupId(groupId);
        if (userGroups.isEmpty()) {
            throw new BusinessException(ErrorCode.GROUP_HAS_NO_USERS);
        }

        return userGroups.stream()
                .map(userGroup -> userGroup.getUser().getId())
                .collect(Collectors.toList());
    }
}