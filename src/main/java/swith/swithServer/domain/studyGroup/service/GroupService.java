package swith.swithServer.domain.studyGroup.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.studyGroup.dto.GroupRequestDto;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final UserGroupRepository userGroupRepository;


    //id로 찾기
    @Transactional
    public StudyGroup getGroupById(Long id){
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(()->new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));
        return studyGroup;
    }


    //groupId,groupPw에 매칭되는 그룹 찾기
    @Transactional
    public StudyGroup getGroupByIdPw(GroupRequestDto groupRequestDto){
        StudyGroup studyGroup = groupRepository.findByGroupIdAndGroupPw(groupRequestDto.getGroupId(), groupRequestDto.getGroupPw())
                .orElseThrow(()->new BusinessException(ErrorCode.GROUP_LOGIN_ERROR));
        return studyGroup;
    }

    //사용자의 스터디 가입 여부 확인
    @Transactional
    public boolean isUserInGroup(User user, StudyGroup studyGroup){
        return userGroupRepository.existsByUserAndStudyGroup(user, studyGroup);
    }

    //notice 수정
    @Transactional
    public StudyGroup updateNotice(Long id, String notice){
        StudyGroup studyGroup = groupRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));

        studyGroup.updateNotice(notice);
        return groupRepository.save(studyGroup);
    }
}
