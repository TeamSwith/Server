package swith.swithServer.domain.userGroup.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.study.dto.MessageResponse;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.user.dto.UserNicknameImageResponse;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.dto.UserGroupRequest;
import swith.swithServer.domain.userGroup.service.UserGroupService;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.global.response.ApiResponse;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/user-group")
@RequiredArgsConstructor
@Tag(name="스터디 그룹 - 사용자")
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final OauthService authService;
    private final GroupService groupService;
    private final UserGroupRepository userGroupRepository;


    //매핑 테이블 생성
    @PostMapping("/create")
    @Operation(summary = "스터디 가입하기")
    public ApiResponse<MessageResponse> createUserGroup(
            @RequestBody UserGroupRequest userGroupRequest){
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(userGroupRequest.getGroupId());
        userGroupService.createUserGroup(user, studyGroup);
        studyGroup.updateMemberNum(studyGroup.getMemberNum()+1);
        return new ApiResponse<>(201, MessageResponse.from("redirect:http://localhost:8080/api/group/"+studyGroup.getId()));
    }


    @GetMapping("/{groupId}/users")
    @Operation(summary = "그룹 사용자 닉네임과 사진 조회", description = "groupId를 사용하여 그룹에 속한 사용자들의 닉네임과 사진을 반환")
    public ApiResponse<List<UserNicknameImageResponse>> getGroupUsers(
            @Parameter(description = "조회할 그룹 ID", required = true)
            @PathVariable(name = "groupId") Long groupId) {

        StudyGroup studyGroup = groupService.getGroupById(groupId);

        List<UserNicknameImageResponse> users = userGroupRepository.findAllByStudyGroup(studyGroup).stream()
                .map(userGroup -> UserNicknameImageResponse.from(userGroup.getUser()))
                .collect(Collectors.toList());

        return new ApiResponse<>(200, users);
    }
}


