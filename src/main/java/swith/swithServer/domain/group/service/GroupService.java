package swith.swithServer.domain.group.service;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swith.swithServer.domain.group.domain.GroupDomain;
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
                null, // groupId는 자동 생성되므로 null로 설정
                request.getGroupInsertId(), // 사용자 입력 ID
                request.getGroupPw(),
                request.getGroupName(),
                request.getMaxNum(),
                request.getMemberNum(),
                request.getSubject(),
                request.getPeriod(),
                request.getCommunication(),
                null // notice는 기본값으로 null
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
//
//    public boolean createGroup(GroupCreateRequest request) {
//        // Check if groupId is unique
//        if (groupRepository.existsByGroupId(request.getGroupId())) {
//            return false; // Group ID already exists
//        }
//
//        // Create new group
//        GroupDomain group = new GroupDomain();
//        group.setGroupName(request.getGroupId()); // Optional if groupName is different
//        group.setGroupPw(request.getGroupPw());
//        group.setMaxNum(0); // Default value
//        group.setMemberNum(0); // Default value
//        groupRepository.save(group);
//        return true;
//    }


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import swith.swithServer.domain.group.domain.GroupDomain;
//import swith.swithServer.domain.group.repository.GroupRepository;
//
//@Service
//public class GroupService {
//
//    // 스터디 그룹 생성
//    @Autowired
//    private GroupRepository groupRepository;
//
//    public GroupDomain createGroup(GroupDomain group) {
//        return groupRepository.save(group);
//    }

//    // 스터디 그룹 삭제
//    public void deleteGroup(Long groupId) {
//        if (!groupRepository.existsById(groupId)) {
//            throw new IllegalArgumentException("해당 그룹 ID가 존재하지 않습니다.");
//        }
//        groupRepository.deleteById(groupId);
//    }
//
//    // 스터디 그룹 ID 가져오기
//    public GroupDomain getGroupById(Long groupId) {
//        return groupRepository.findById(groupId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 그룹 ID가 존재하지 않습니다."));
//    }
//
//    // 스터디 생성 시 정보 POST
//    public GroupDomain updateGroupDetails(Long groupId, GroupUpdateRequest updateRequest) {
//        GroupDomain group = groupRepository.findById(groupId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 그룹 ID가 존재하지 않습니다."));
//
//        // 업데이트할 필드 설정
//        group.setGroupName(updateRequest.getGroupName());
//        group.setMaxNum(updateRequest.getMaxNum());
//        group.setPeriod(updateRequest.getPeriod());
//        group.setSubject(updateRequest.getSubject());
//        group.setCommunication(updateRequest.getCommunication());
//        return groupRepository.save(group);
//    }
//
//    // 스터디 생성 시 정보 보여주기
//    public GroupDomain getGroupDetails(Long groupId) {
//        return groupRepository.findById(groupId)
//                .orElseThrow(() -> new IllegalArgumentException("해당 그룹 ID가 존재하지 않습니다."));
//    }
//}