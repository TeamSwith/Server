package swith.swithServer.domain.usergroup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.usergroup.service.UserGroupService;
import swith.swithServer.global.response.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

import java.util.List;

@RestController
@RequestMapping("/user-groups")
@RequiredArgsConstructor
public class UserGroupController {

    private final UserGroupService userGroupService;

    // 그룹 ID로 유저 ID 리스트 조회 API
    @GetMapping("/{groupId}")
    @Operation(summary = "Get user ID", description = "USing group ID.")
    public ApiResponse<List<Long>> getUsersByGroupId(
            @Parameter(description = "ID of the group to fetch user IDs", required = true)
            @PathVariable(name = "groupId") Long groupId) {
        List<Long> userIds = userGroupService.findUserIdsByGroupId(groupId);
        return new ApiResponse<>(200, userIds);
    }
}