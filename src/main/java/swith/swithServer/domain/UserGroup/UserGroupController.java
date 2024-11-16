package swith.swithServer.domain.UserGroup;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import swith.swithServer.domain.Group.Entity.Group;
import swith.swithServer.domain.Group.service.GroupService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.service.UserService;

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
    public ResponseEntity<String> createUserGroup(
            @RequestParam Long userId,
            @RequestParam Long groupId){
        User user = userService.getUserById(userId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid User Id"));
        Group group = groupService.getGroupById(groupId)
                .orElseThrow(()-> new IllegalArgumentException("Invalid Group Id"));
        userGroupService.createUserGroup(user, group);
        group.updateMemberNum(group.getMemberNum()+1);
        return ResponseEntity.ok("Mapping created");
    }


}
