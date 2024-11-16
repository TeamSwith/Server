package swith.swithServer.domain.userGroup.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.service.UserService;
import swith.swithServer.domain.userGroup.service.UserGroupService;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.response.ApiResponse;

@RestController
@RequestMapping("/user-group")
@RequiredArgsConstructor
//@NoArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;
    private final UserService userService;
    private final GroupService groupService;

    //매핑 테이블 생성
    @PostMapping("/create")
    public ApiResponse<String> createUserGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId){
        User user = userService.getUserById(userId)
                .orElseThrow(()-> new BusinessException(ErrorCode.USER_DOESNT_EXIST));
        StudyGroup studyGroup = groupService.getGroupById(groupId)
                .orElseThrow(()-> new BusinessException(ErrorCode.GROUP_DOESNT_EXIST));
        userGroupService.createUserGroup(user, studyGroup);
        studyGroup.updateMemberNum(studyGroup.getMemberNum()+1);
        //return ResponseEntity.ok("Mapping created");
        return new ApiResponse<>(201, "Mapping created");
    }


}
