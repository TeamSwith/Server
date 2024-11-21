package swith.swithServer.domain.studyGroup.service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.dto.GroupCreateRequest;
import swith.swithServer.domain.studyGroup.dto.GroupUpdateRequest;
import swith.swithServer.domain.studyGroup.dto.GroupResponse;
import swith.swithServer.domain.studyGroup.repository.GroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;


@Service
@RequiredArgsConstructor
public class GroupService {

    @Autowired
    private final GroupRepository groupRepository;

    // 스터디 생성
    @Transactional
    public StudyGroup createGroup(GroupCreateRequest request) {
        if (groupRepository.existsByGroupInsertId(request.getGroupInsertId())) { // 변경된 부분
            throw new BusinessException(ErrorCode.GROUP_INSERT_ID_ALREADY_EXISTS); // 변경된 부분
        }

        StudyGroup studyGroup = new StudyGroup(
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

        return groupRepository.save(studyGroup);
    }

    // GET groupInsertID
    public String findGroupInsertIdByGroupId(Long groupId) {
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));
        return studyGroup.getGroupInsertId();
    }

    // 그룹 정보 수정
    @Transactional
    public void updateGroup(Long groupId, GroupUpdateRequest updateRequest) {
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        studyGroup.setGroupName(updateRequest.getGroupName());
        studyGroup.setMaxNum(updateRequest.getMaxNum());
        studyGroup.setSubject(updateRequest.getSubject());
        studyGroup.setPeriod(updateRequest.getPeriod());
        studyGroup.setCommunication(updateRequest.getCommunication());

        groupRepository.save(studyGroup);
    }

    // groupId로 그룹정보 GET
    public GroupResponse getGroupDetails(Long groupId) {
        StudyGroup studyGroup = groupRepository.findById(groupId)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_GROUP_ID));

        return GroupResponse.builder()
                .groupName(studyGroup.getGroupName())
                .groupInsertId(studyGroup.getGroupInsertId())
                .maxNum(studyGroup.getMaxNum())
                .subject(studyGroup.getSubject())
                .period(studyGroup.getPeriod())
                .communication(studyGroup.getCommunication())
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