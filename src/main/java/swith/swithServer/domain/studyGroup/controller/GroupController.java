package swith.swithServer.domain.studyGroup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.sse.service.SseEmitters;
import swith.swithServer.domain.study.dto.MessageResponse;
import swith.swithServer.domain.studyGroup.dto.*;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.domain.userGroup.service.UserGroupService;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.response.ApiResponse;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.global.oauth.service.OauthService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Tag(name="스터디 그룹(studyGroup)")
public class GroupController {
    private final GroupService groupService;
    private final OauthService authService;
    private final UserGroupService userGroupService;
    private final UserGroupRepository userGroupRepository;
    private final SseEmitters sseEmitters;

    @PostMapping("/join")
    @Operation(summary = "스터디 그룹 가입 여부 확인")
    public ApiResponse<?> joinGroup(@RequestBody GroupRequest groupRequest) {

        //id와 pw가 매칭되는 group 여부 확인
        StudyGroup studyGroup = groupService.getGroupByInsertIdPw(groupRequest);
        //로그인 유저 받아오기
        User user = authService.getLoginUser();
        //가입 여부 확인
        boolean isUserInGroup = groupService.isUserInGroup(user, studyGroup);
        if (isUserInGroup) {
            //가입되어 있을 때
            return new ApiResponse<>(200, GroupLoginResponse.from(studyGroup.getId(), "이미 가입되어 있음"));
        } else {
            //가입되어 있지 않을 때 group 의 정원 확인
            if (studyGroup.getMemberNum() < studyGroup.getMaxNum()) {
                return new ApiResponse<>(200, GroupLoginResponse.from(studyGroup.getId(), "가입 전"));
            } else {
                throw new BusinessException(ErrorCode.MAX_MEMBER);
            }
        }
    }

    @GetMapping("/{id}/getMem")
    @Operation(summary = "현재 스터디 인원 가져오기")
    public ApiResponse<GroupMemResponse> getMemberNum(@PathVariable Long id) {
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(id);
        return new ApiResponse<>(200, GroupMemResponse.from(studyGroup));
    }

    @GetMapping("/{id}/notice")
    @Operation(summary = "공지사항 가져오기")
    public ApiResponse<GroupNoticeResponse> getNotice(
            @PathVariable Long id) {
        User user = authService.getLoginUser();

        StudyGroup studyGroup = groupService.getGroupById(id);
        return new ApiResponse<>(200, GroupNoticeResponse.from(studyGroup));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "공지사항 수정")
    public ApiResponse<GroupNoticeResponse> updateNotice(
            @PathVariable Long id,
            @RequestBody StringRequest stringRequest) {
        User user = authService.getLoginUser();
        StudyGroup updatedNotice = groupService.updateNotice(id, stringRequest);

        StudyGroup studyGroup = groupService.getGroupById(id);

        List<Long> userIds = userGroupRepository.findAllByStudyGroup(studyGroup).stream()
                .map(userGroup -> userGroup.getUser().getId())
                .collect(Collectors.toList());


        for ( Long userId : userIds ){
            sseEmitters.sendSse(userId,"Notice",updatedNotice.getNotice()); // 모든 클라이언트에 알림 전송
        }

        return new ApiResponse<>(200, GroupNoticeResponse.from(updatedNotice));
    }

    // 그룹 생성 API
    @PostMapping("/create")
    @Operation(summary = "스터디 그룹 생성", description = "")
    public ApiResponse<StudyGroup> createGroup(@RequestBody GroupCreateRequest request) {
        StudyGroup createdStudyGroup = groupService.createGroup(request);
        //로그인 유저 받아오기
        User user = authService.getLoginUser();
        userGroupService.createUserGroup(user, createdStudyGroup);
        return new ApiResponse<>(201, createdStudyGroup);
    }

    // groupId로 그룹 정보 받아오는 API
    @GetMapping("/{id}/details")
    @Operation(summary = "스터디 그룹 정보 조회", description = "using groupId")
    public ApiResponse<GroupResponse> getGroupDetails(
            @Parameter(description = "ID of the group to fetch details", required = true)
            @PathVariable(name = "id") Long id) {
        GroupResponse response = groupService.getGroupDetails(id);
        return new ApiResponse<>(200, response);
    }

    // groupID로 groupInsertId 가져오는 API
    @GetMapping("/{id}")
    @Operation(summary = "스터디 그룹 ID 조회", description = "using group_id")
//    public ApiResponse<String> getGroupInsertId(
    public ApiResponse<GroupIdAndInsertIdResponse> getGroupInsertId(

            @Parameter(description = "ID of the group to fetch the insert ID", required = true)
            @PathVariable(name = "id") Long id) {
        StudyGroup studyGroup = groupService.getGroupById(id);
        return new ApiResponse<>(200, GroupIdAndInsertIdResponse.from(studyGroup));
//        String groupInsertId = groupService.findGroupInsertIdByGroupId(groupId);
//        return new ApiResponse<>(200, groupInsertId);
    }

    // 스터디 그룹 정보 수정 API
    @PatchMapping("/{id}/details")
    @Operation(summary = "스터디 그룹 정보 수정", description = "Using groupId")
    public ApiResponse<GroupResponse> updateGroup(
            @Parameter(description = "ID of the group to be updated", required = true)
            @PathVariable(name = "id") Long id,
            @RequestBody GroupUpdateRequest updateRequest) {
        return new ApiResponse<>(200, groupService.updateGroupAndGetDetails(id, updateRequest));

    }

    // 스터디 그룹 삭제 API
    @DeleteMapping("/{id}")
    @Operation(summary = "스터디 그룹 삭제", description = "Using id")
    public ApiResponse<GroupResponse> deleteGroup(
            @Parameter(description = "ID of the group to be deleted", required = true)
            @PathVariable(name = "id") Long id) {
        GroupResponse deletedGroup = groupService.deleteGroup(id);
        return new ApiResponse<>(200, deletedGroup);
    }


}