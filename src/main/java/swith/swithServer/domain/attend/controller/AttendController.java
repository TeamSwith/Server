package swith.swithServer.domain.attend.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.attend.dto.AttendResponse;
import swith.swithServer.domain.attend.entity.Attend;
import swith.swithServer.domain.attend.service.AttendService;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.global.response.ApiResponse;

@RestController
@RequestMapping("/group/{id}/study/{studyId}")
@RequiredArgsConstructor
@Tag(name="출석")
public class AttendController {
    private final OauthService authService;
    private final GroupService groupService;
    private final AttendService attendService;

    @PatchMapping("/attend")
    @Operation(summary = "출석 상태 변경")
    public ApiResponse<AttendResponse> updateAttend(
            @PathVariable Long id,
            @PathVariable Long studyId
    ){
        StudyGroup studyGroup = groupService.getGroupById(id);
        User user = authService.getLoginUser();
        Attend updatedAttend = attendService.updateAttend(user, studyId);
        return new ApiResponse<>(200, AttendResponse.from(updatedAttend));
    }
}
