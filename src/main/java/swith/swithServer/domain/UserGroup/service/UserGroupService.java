package swith.swithServer.domain.userGroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.userGroup.entity.UserGroup;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;

    //매핑테이블 생성
    @Transactional
    public UserGroup createUserGroup(User user, StudyGroup studyGroup){
        UserGroup userGroup = new UserGroup(user, studyGroup);
        return userGroupRepository.save(userGroup);
    }

}
