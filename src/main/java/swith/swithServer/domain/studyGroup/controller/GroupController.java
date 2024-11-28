package swith.swithServer.domain.studyGroup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.study.dto.MessageResponse;
import swith.swithServer.domain.studyGroup.dto.*;
import swith.swithServer.domain.userGroup.service.UserGroupService;
import swith.swithServer.global.response.ApiResponse;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.service.UserService;
import swith.swithServer.global.oauth.service.OauthService;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Tag(name="스터디 그룹(studyGroup)")
public class GroupController {
    private final GroupService groupService;
    private final OauthService authService;
    private final UserGroupService userGroupService;

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
            return new ApiResponse<>(200, GroupLoginResponse.from(studyGroup.getId(), "이미 가입되어 있음", "redirect:/" + studyGroup.getId()));
        } else {
            //가입되어 있지 않을 때 group 의 정원 확인
            if (studyGroup.getMemberNum() < studyGroup.getMaxNum()) {
                return new ApiResponse<>(200, GroupLoginResponse.from(studyGroup.getId(), "가입 전", "redirect:http://localhost:8080/api/user-group/create"));
            } else {
                return new ApiResponse<>(404, MessageResponse.from("정원을 초과했습니다."));
            }
        }
    }

    @GetMapping("/{id}/getMem")
    @Operation(summary = "현재 스터디 인원 가져오기")
    public ApiResponse<Long> getMemberNum(@PathVariable Long id) {
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(id);
        Long memberNum = studyGroup.getMemberNum();
        return new ApiResponse<>(200, memberNum);
    }

    @GetMapping("/{id}/notice")
    @Operation(summary = "공지사항 가져오기")
    public ApiResponse<String> getNotice(
            @PathVariable Long id) {
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(id);
        String notice = studyGroup.getNotice();
        return new ApiResponse<>(200, notice);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "공지사항 수정")
    public ApiResponse<String> updateNotice(
            @PathVariable Long id,
            @RequestBody StringRequest stringRequest) {
        User user = authService.getLoginUser();
        StudyGroup updatedNotice = groupService.updateNotice(id, stringRequest);
        return new ApiResponse<>(200, updatedNotice.getNotice());
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
    @GetMapping("/{groupId}/details")
    @Operation(summary = "스터디 그룹 정보 조회", description = "using groupId")
    public ApiResponse<GroupResponse> getGroupDetails(
            @Parameter(description = "ID of the group to fetch details", required = true)
            @PathVariable(name = "groupId") Long groupId) {
        GroupResponse response = groupService.getGroupDetails(groupId);
        return new ApiResponse<>(200, response);
    }

    // groupID로 groupInsertId 가져오는 API
    @GetMapping("/{groupId}")
    @Operation(summary = "스터디 그룹 ID 조회", description = "using group_id")
//    public ApiResponse<String> getGroupInsertId(
    public ApiResponse<GroupIdAndInsertIdResponse> getGroupInsertId(

            @Parameter(description = "ID of the group to fetch the insert ID", required = true)
            @PathVariable(name = "groupId") Long groupId) {
        StudyGroup studyGroup = groupService.getGroupById(groupId);
        return new ApiResponse<>(200, GroupIdAndInsertIdResponse.from(studyGroup));
//        String groupInsertId = groupService.findGroupInsertIdByGroupId(groupId);
//        return new ApiResponse<>(200, groupInsertId);
    }

    // 스터디 그룹 정보 수정 API
    @PatchMapping("/{groupId}/details")
    @Operation(summary = "스터디 그룹 정보 수정", description = "Using groupId")
    public ApiResponse<GroupResponse> updateGroup(
            @Parameter(description = "ID of the group to be updated", required = true)
            @PathVariable(name = "groupId") Long groupId,
            @RequestBody GroupUpdateRequest updateRequest) {
        return new ApiResponse<>(200, groupService.updateGroupAndGetDetails(groupId, updateRequest));

    }

    // 스터디 그룹 삭제 API
    @DeleteMapping("/{groupId}")
    @Operation(summary = "스터디 그룹 삭제", description = "Using GroupId")
    public ApiResponse<GroupResponse> deleteGroup(
            @Parameter(description = "ID of the group to be deleted", required = true)
            @PathVariable(name = "groupId") Long groupId) {
        GroupResponse deletedGroup = groupService.deleteGroup(groupId);
        return new ApiResponse<>(200, deletedGroup);
    }


}

