package swith.swithServer.domain.usergroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.usergroup.entity.UserGroup;
import swith.swithServer.domain.usergroup.repository.UserGroupRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserGroupService {

    private final UserGroupRepository userGroupRepository;

    public List<Long> findUserIdsByGroupId(Long groupId) {
        List<UserGroup> userGroups = userGroupRepository.findByStudyGroupGroupId(groupId);
        return userGroups.stream()
                .map(userGroup -> userGroup.getUser().getId())
                .collect(Collectors.toList());
    }
}