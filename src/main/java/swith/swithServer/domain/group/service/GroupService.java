package swith.swithServer.domain.group.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.group.entity.GroupDomain;
import swith.swithServer.domain.group.dto.GroupCreateRequest;
import swith.swithServer.domain.group.dto.GroupUpdateRequest;
import swith.swithServer.domain.group.dto.GroupResponse;
import swith.swithServer.domain.group.repository.GroupRepository;

@Service
@RequiredArgsConstructor
public class GroupService {

    @Autowired
    private final GroupRepository groupRepository;

    // 스터디 생성
    public GroupDomain createGroup(GroupCreateRequest request) {
        GroupDomain group = new GroupDomain(
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
        GroupDomain group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));
        return group.getGroupInsertId();
    }

    // 그룹 정보 수정
    public void updateGroup(Long groupId, GroupUpdateRequest updateRequest) {
        GroupDomain group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));

        group.setGroupName(updateRequest.getGroupName());
        group.setMaxNum(updateRequest.getMaxNum());
        group.setSubject(updateRequest.getSubject());
        group.setPeriod(updateRequest.getPeriod());
        group.setCommunication(updateRequest.getCommunication());

        groupRepository.save(group);
    }

    // groupId로 그룹정보 GET
    public GroupResponse getGroupDetails(Long groupId) {
        GroupDomain group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + groupId));

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
    public void deleteGroup(Long groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Group not found with id: " + groupId);
        }
        groupRepository.deleteById(groupId);
    }
}