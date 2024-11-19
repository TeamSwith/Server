package swith.swithServer.domain.group.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.global.response.ApiResponse;
import swith.swithServer.domain.group.dto.GroupCreateRequest;
import swith.swithServer.domain.group.dto.GroupUpdateRequest;
import swith.swithServer.domain.group.dto.GroupResponse;
import swith.swithServer.domain.group.service.GroupService;
import swith.swithServer.domain.group.entity.GroupDomain;

@RestController
@RequestMapping("/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    // 그룹 생성 API
    @PostMapping("/create")
    public ApiResponse<GroupDomain> createGroup(@RequestBody GroupCreateRequest request) {
        GroupDomain createdGroup = groupService.createGroup(request);
        return new ApiResponse<>(201, createdGroup); // 201 Created
    }

    // groupID로 groupInsertId 가져오기
    @GetMapping("/insert-id")
    public ApiResponse<String> getGroupInsertId(@RequestParam(name = "groupId") Long groupId) {
        String groupInsertId = groupService.findGroupInsertIdByGroupId(groupId);
        return new ApiResponse<>(200, groupInsertId); // 200 OK
    }

    // groupId로 group 데이터 수정
    @PutMapping("/update")
    public ApiResponse<String> updateGroup(
            @RequestParam(name = "groupId") Long groupId,
            @RequestBody GroupUpdateRequest updateRequest) {
        groupService.updateGroup(groupId, updateRequest);
        return new ApiResponse<>(200, "Group updated successfully"); // 200 OK
    }

    // groupId를 쿼리 파라미터로 받아 group 정보를 반환
    @GetMapping("/details")
    public ApiResponse<GroupResponse> getGroupDetails(@RequestParam(name = "groupId") Long groupId) {
        GroupResponse response = groupService.getGroupDetails(groupId);
        return new ApiResponse<>(200, response); // 200 OK
    }

    // groupId를 기준으로 그룹 삭제
    @DeleteMapping("/delete")
    public ApiResponse<Void> deleteGroup(@RequestParam(name = "groupId") Long groupId) {
        groupService.deleteGroup(groupId);
        return new ApiResponse<>(204, null); // 204 No Content
    }
}