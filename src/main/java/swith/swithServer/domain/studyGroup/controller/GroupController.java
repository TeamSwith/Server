package swith.swithServer.domain.studyGroup.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.studyGroup.dto.GroupRequestDto;
import swith.swithServer.domain.studyGroup.dto.GroupResponseDto;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.user.service.UserService;
import swith.swithServer.global.response.ApiResponse;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/group")
@RequiredArgsConstructor
@Tag(name="스터디 그룹")
public class GroupController {
    private final GroupService groupService;
    private final UserService userService;



    @PostMapping("/join")
    @Operation(summary = "스터디 그룹 가입 여부 확인")
    public ApiResponse<?> joinGroup(@RequestBody GroupRequestDto groupRequestDto){

        //id와 pw가 매칭되는 group 여부 확인
        StudyGroup studyGroup = groupService.getGroupByIdPw(groupRequestDto.getGroupId(), groupRequestDto.getGroupPw());

        //user 조회
        User user = userService.getUserById(groupRequestDto.getUserId());

        //가입 여부 확인
        boolean isUserInGroup = groupService.isUserInGroup(user, studyGroup);

        if (isUserInGroup) {
                //가입되어 있을 때
                return new ApiResponse<>(200, GroupResponseDto.from(studyGroup.getId(), "이미 가입되어 있음", "redirect:/"+studyGroup.getId()));
//
        }
        else {
                //가입되어 있지 않을 때 group 의 정원 확인
            if(studyGroup.getMemberNum()< studyGroup.getMaxNum()) {
                return new ApiResponse<>(200,GroupResponseDto.from(studyGroup.getId(), "가입 전","redirect:http://localhost:8080/api/user-group/create"));
            }
            else{
                return new ApiResponse<>(404,"정원을 초과했습니다.");
            }
        }

    }

    @GetMapping("/{id}")
    @Operation(summary = "공지사항 가져오기")
    public ApiResponse<String> getNotice(
            @PathVariable Long id){
        StudyGroup studyGroup = groupService.getGroupById(id);
        String notice = studyGroup.getNotice();
        return new ApiResponse<>(200,notice);

    }

    @PatchMapping("/{id}")
    @Operation(summary = "공지사항 수정")
    public ApiResponse<String> updateNotice(
            @PathVariable Long id,
            @RequestParam String notice){
        StudyGroup updatedNotice = groupService.updateNotice(id, notice);
        return new ApiResponse<>(200, updatedNotice.getNotice());
    }


}
