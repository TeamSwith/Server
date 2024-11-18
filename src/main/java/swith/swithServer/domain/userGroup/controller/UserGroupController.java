package swith.swithServer.domain.userGroup.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.service.UserService;
import swith.swithServer.domain.userGroup.dto.UserGroupDto;
import swith.swithServer.domain.userGroup.service.UserGroupService;
import swith.swithServer.global.response.ApiResponse;

@RestController
@RequestMapping("/user-group")
@RequiredArgsConstructor
@Tag(name="스터디 그룹 새로 참가")
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final UserService userService;
    private final GroupService groupService;

    //매핑 테이블 생성
    @PostMapping("/create")
    @Operation(summary = "스터디 가입하기")
    public ApiResponse<String> createUserGroup(
            @RequestBody UserGroupDto userGroupDto){
        User user = userService.getUserById(userGroupDto.getUserId());
        StudyGroup studyGroup = groupService.getGroupById(userGroupDto.getGroupId());
        userGroupService.createUserGroup(user, studyGroup);
        studyGroup.updateMemberNum(studyGroup.getMemberNum()+1);
        return new ApiResponse<>(201, "redirect:http://localhost:8080/api/group/"+studyGroup.getId());
    }


}
