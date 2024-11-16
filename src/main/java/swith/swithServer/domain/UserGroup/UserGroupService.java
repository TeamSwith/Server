package swith.swithServer.domain.UserGroup;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.Group.Entity.Group;
import swith.swithServer.domain.UserGroup.entity.UserGroup;
import swith.swithServer.domain.user.entity.User;

@Service
@RequiredArgsConstructor
//@NoArgsConstructor
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;

    //매핑테이블 생성
    @Transactional
    public UserGroup createUserGroup(User user, Group group){
        UserGroup userGroup = new UserGroup(user, group);
        return userGroupRepository.save(userGroup);
    }

}
