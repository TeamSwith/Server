package swith.swithServer.domain.Group.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.Group.Entity.Group;
import swith.swithServer.domain.Group.repository.GroupRepository;
import swith.swithServer.domain.Study.Entity.Study;
import swith.swithServer.domain.UserGroup.UserGroupRepository;
import swith.swithServer.domain.user.entity.User;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;

    //테스트 용
    @Transactional
    public Group createGroup(String groupId, String groupPw, int maxNum, int memberNum, String subject, String period, String communication){
        Group group = new Group(groupId,groupPw,maxNum,memberNum,subject,period,communication);
        return groupRepository.save(group);
    }

    //id로 찾기
    @Transactional
    public Optional<Group> getGroupById(Long id){
        return groupRepository.findById(id);
    }


    //groupId,groupPw에 매칭되는 그룹 찾기
    @Transactional
    public Optional<Group> getGroupByIdPw(String groupId, String groupPw){
        return groupRepository.findByGroupIdAndGroupPw(groupId, groupPw);
    }

    //사용자의 스터디 가입 여부 확인
    @Transactional
    public boolean isUserInGroup(User user, Group group){
        return userGroupRepository.existsByUserAndGroup(user, group);
    }

    //notice 수정
    @Transactional
    public Group updateNotice(Long id, String notice){
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Group ID: "+id));
        group.updateNotice(notice);
        return groupRepository.save(group);
    }
}
