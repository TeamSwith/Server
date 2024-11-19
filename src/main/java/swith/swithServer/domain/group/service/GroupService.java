package swith.swithServer.domain.group.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.group.entity.Group;
import swith.swithServer.domain.group.dto.GroupCreateRequest;
import swith.swithServer.domain.group.dto.GroupUpdateRequest;
import swith.swithServer.domain.group.dto.GroupResponse;
import swith.swithServer.domain.group.repository.GroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;


@Service
@RequiredArgsConstructor
public class GroupService {

    @Autowired
    private final GroupRepository groupRepository;

    // 스터디 생성
    @Transactional
    public Group createGroup(GroupCreateRequest request) {
        if (groupRepository.existsByGroupInsertId(request.getGroupInsertId())) { // 변경된 부분
            throw new BusinessException(ErrorCode.GROUP_INSERT_ID_ALREADY_EXISTS); // 변경된 부분
        }

        Group group = new Group(
                null,
                request.getGroupInsertId(),
                request.getGroupPw(),
                request.getGroupName(),
                request.getMaxNum(),
                request.getMemberNum(),
                request.getSubject(),
                request.getPeriod(),
                request.getCommunication(),
                null
        );

        return groupRepository.save(group);
    }

    // GET groupInsertID
    public String findGroupInsertIdByGroupId(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));
        return group.getGroupInsertId();
    }

    // 그룹 정보 수정
    @Transactional
    public void updateGroup(Long groupId, GroupUpdateRequest updateRequest) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        group.setGroupName(updateRequest.getGroupName());
        group.setMaxNum(updateRequest.getMaxNum());
        group.setSubject(updateRequest.getSubject());
        group.setPeriod(updateRequest.getPeriod());
        group.setCommunication(updateRequest.getCommunication());

        groupRepository.save(group);
    }

    // groupId로 그룹정보 GET
    public GroupResponse getGroupDetails(Long groupId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        return GroupResponse.builder()
                .groupName(group.getGroupName())
                .groupInsertId(group.getGroupInsertId())
                .maxNum(group.getMaxNum())
                .subject(group.getSubject())
                .period(group.getPeriod())
                .communication(group.getCommunication())
                .build();
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