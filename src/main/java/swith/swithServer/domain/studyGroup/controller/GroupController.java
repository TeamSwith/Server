package swith.swithServer.domain.studyGroup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.global.response.ApiResponse;
import swith.swithServer.domain.studyGroup.dto.GroupCreateRequest;
import swith.swithServer.domain.studyGroup.dto.GroupUpdateRequest;
import swith.swithServer.domain.studyGroup.dto.GroupResponse;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    // 그룹 생성 API
    @PostMapping("")
    @Operation(summary = "Create new group", description = "")
    public ApiResponse<StudyGroup> createGroup(@RequestBody GroupCreateRequest request) {
        StudyGroup createdStudyGroup = groupService.createGroup(request);
        return new ApiResponse<>(201, createdStudyGroup);
    }

    // groupID로 groupInsertId 가져오는 API
    @GetMapping("/{groupId}")
    @Operation(summary = "Get group insert ID", description = "using group_id")
    public ApiResponse<String> getGroupInsertId(
            @Parameter(description = "ID of the group to fetch the insert ID", required = true)
            @PathVariable(name = "groupId") Long groupId) {
        String groupInsertId = groupService.findGroupInsertIdByGroupId(groupId);
        return new ApiResponse<>(200, groupInsertId);
    }

    // groupId로 group 데이터 수정하는 API
    @PutMapping("/{groupId}")
    @Operation(summary = "Update group data", description = "Using groupId")
    public ApiResponse<String> updateGroup(
            @Parameter(description = "ID of the group to be updated", required = true)
            @PathVariable(name = "groupId") Long groupId,
            @RequestBody GroupUpdateRequest updateRequest) {
        groupService.updateGroup(groupId, updateRequest);
        return new ApiResponse<>(200, "Group updated successfully");
    }

    // groupId로 그룹 정도 받아오는 API
    @GetMapping("/{groupId}/details")
    @Operation(summary = "Get group details", description = "using groupId")
    public ApiResponse<GroupResponse> getGroupDetails(
            @Parameter(description = "ID of the group to fetch details", required = true)
            @PathVariable(name = "groupId") Long groupId) {
        GroupResponse response = groupService.getGroupDetails(groupId);
        return new ApiResponse<>(200, response);
    }

    // groupId를 기준으로 그룹 삭제하는 API
    @DeleteMapping("/{groupId}")
    @Operation(summary = "Delete group", description = "Using GroupId")
    public ApiResponse<Void> deleteGroup(
            @Parameter(description = "ID of the group to be deleted", required = true)
            @PathVariable(name = "groupId") Long groupId) {
        groupService.deleteGroup(groupId);
        return new ApiResponse<>(204, null);
    }
}
