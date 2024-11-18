package swith.swithServer.domain.group.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.group.dto.GroupCreateRequest;
import swith.swithServer.domain.group.dto.GroupUpdateRequest;
import swith.swithServer.domain.group.dto.GroupResponse;
import swith.swithServer.domain.group.service.GroupService;
import swith.swithServer.domain.group.domain.GroupDomain;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/groups")
//@RequiredArgsConstructor
public class GroupController {
    @Autowired
    private GroupService groupService;

    // 그룹 생성 API
    @PostMapping("/create")
    public ResponseEntity<GroupDomain> createGroup(@RequestBody GroupCreateRequest request) {
        GroupDomain createdGroup = groupService.createGroup(request);
        return ResponseEntity.ok(createdGroup);
    }

    // groupID로 groupInsertId 가져오기
    @GetMapping("/insert-id")
    public ResponseEntity<String> getGroupInsertId(@RequestParam(name = "groupId") Long groupId) {
        String groupInsertId = groupService.findGroupInsertIdByGroupId(groupId);
        return ResponseEntity.ok(groupInsertId);
    }

    // groupId로 group 데이터 수정
    @PutMapping("/update")
    public ResponseEntity<String> updateGroup(
            @RequestParam(name = "groupId") Long groupId,
            @RequestBody GroupUpdateRequest updateRequest) {
        groupService.updateGroup(groupId, updateRequest);
        return ResponseEntity.ok("Group updated successfully");
    }

    // groupId를 쿼리 파라미터로 받아 group 정보를 반환
    @GetMapping("/details")
    public ResponseEntity<GroupResponse> getGroupDetails(@RequestParam(name = "groupId") Long groupId) {
        GroupResponse response = groupService.getGroupDetails(groupId);
        return ResponseEntity.ok(response);
    }

    // groupId를 기준으로 그룹 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGroup(@RequestParam(name = "groupId") Long groupId) {
        groupService.deleteGroup(groupId);
        return ResponseEntity.ok("Group deleted successfully");
    }

//    @GetMapping("/insert-id")
//    public ResponseEntity<String> getGroupInsertId(@RequestParam Long groupId) {
//        String groupInsertId = groupService.findGroupInsertIdByGroupId(groupId);
//        return ResponseEntity.ok(groupInsertId);
//    }

}
//    @PostMapping
//    public ResponseEntity<String> createGroup(@Valid @RequestBody GroupCreateRequest request) {
//        boolean isCreated = groupService.createGroup(request);
//
//        if (isCreated) {
//            return ResponseEntity.ok("Group created successfully.");
//        } else {
//            return ResponseEntity.badRequest().body("Group ID already exists.");
//        }
//    }


//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import swith.swithServer.domain.group.domain.GroupDomain;
//import swith.swithServer.domain.group.service.GroupService;
//import jakarta.validation.Valid;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import io.swagger.v3.oas.annotations.Operation;
//
//
//@Tag(name = "Group API", description = "스터디 그룹 관리와 관련된 API입니다.") // API 그룹 태그 추가
//@RestController
//@RequestMapping("/group")
//public class GroupController {
//
//    @Autowired
//    private GroupService groupService;
//
//    // 스터디 생성
//    @Operation(summary = "스터디 그룹 생성", description = "새로운 스터디 그룹을 생성합니다.")
//    @PostMapping("/create")
//    public ResponseEntity<GroupDomain> createGroup(@Valid @RequestBody GroupDomain group) {
//        GroupDomain newGroup = groupService.createGroup(group);
//        return ResponseEntity.ok(newGroup);
//    }

//    // 스터디 삭제
//    @DeleteMapping("/delete/{groupId}")
//    public ResponseEntity<String> deleteGroup(@PathVariable Long groupId) {
//        groupService.deleteGroup(groupId);
//        return ResponseEntity.ok("그룹이 성공적으로 삭제되었습니다.");
//    }
//
//    // 스터디 ID GET
//    @GetMapping("/{groupId}")
//    public ResponseEntity<GroupDomain> getGroupById(@PathVariable Long groupId) {
//        GroupDomain group = groupService.getGroupById(groupId);
//        return ResponseEntity.ok(group);
//    }
//
//    // 스터디 세부사항 POST
//    @PostMapping("/update/{groupId}")
//    public ResponseEntity<GroupDomain> updateGroupDetails(
//            @PathVariable Long groupId,
//            @RequestBody GroupUpdateRequest updateRequest) {
//        GroupDomain updatedGroup = groupService.updateGroupDetails(groupId, updateRequest);
//        return ResponseEntity.ok(updatedGroup);
//    }
//
//    // 그룹 정보를 가져오는 GET API
//    @GetMapping("/{groupId}")
//    public ResponseEntity<GroupDomain> getGroupDetails(@PathVariable Long groupId) {
//        GroupDomain group = groupService.getGroupDetails(groupId);
//        return ResponseEntity.ok(group);
//    }
