package swith.swithServer.domain.userGroup.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.userGroup.entity.UserGroup;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGroupService {
    private final UserGroupRepository userGroupRepository;
    private final GroupRepository groupRepository;


    //매핑테이블 생성
    @Transactional
    public UserGroup createUserGroup(User user, StudyGroup studyGroup){
        if(userGroupRepository.findByStudyGroupAndUser(studyGroup, user).isPresent()){
            throw new BusinessException(ErrorCode.ALREADY_JOIN);
        }
        UserGroup userGroup = new UserGroup(user, studyGroup);
        studyGroup.updateMemberNum(studyGroup.getMemberNum().incrementAndGet());
        return userGroupRepository.save(userGroup);
    }

    //매핑테이블 삭제
    @Transactional
    public void deleteUserGroup(Long id){
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(()->new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));
        List<UserGroup> userGroups=userGroupRepository.findAllByStudyGroup(studyGroup);
        userGroupRepository.deleteAllInBatch(userGroups);
    }

}
