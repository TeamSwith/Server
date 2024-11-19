package swith.swithServer.domain.usergroup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.usergroup.service.UserGroupService;
import swith.swithServer.global.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/user-groups")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;

    @GetMapping("/users")
    public ApiResponse<List<Long>> getUsersByGroupId(
            @RequestParam("groupId") Long groupId) {
        List<Long> userIds = userGroupService.findUserIdsByGroupId(groupId);
        return new ApiResponse<>(200, userIds);
    }

}