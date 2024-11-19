package swith.swithServer.domain.usergroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.usergroup.entity.UserGroup;
import swith.swithServer.domain.usergroup.repository.UserGroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;

    // groupId로 사용자 ID 목록 조회 API
    public List<Long> findUserIdsByGroupId(Long groupId) {
        List<UserGroup> userGroups = userGroupRepository.findByStudyGroupGroupId(groupId);

        if (userGroups.isEmpty()) {
            throw new BusinessException(ErrorCode.GROUP_DOESNT_EXIST_OR_NO_USERS);
        }

        return userGroups.stream()
                .map(userGroup -> userGroup.getUser().getId())
                .collect(Collectors.toList());
    }
}