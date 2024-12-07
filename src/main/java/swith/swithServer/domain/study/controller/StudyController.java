package swith.swithServer.domain.study.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.study.dto.*;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.domain.study.dto.StudyMonthlyResponse;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.global.response.ApiResponse;

import java.time.LocalDate;

@RestController
@RequestMapping("/group/{id}/study")
@RequiredArgsConstructor
@Tag(name="날짜 별 스터디")
public class StudyController {
    private final StudyService studyService;
    private final GroupService groupService;
    private final OauthService authService;
    private final UserGroupRepository userGroupRepository;

    @PostMapping
    @Operation(summary="스터디 일정 생성")
    public ApiResponse<StudyResponse> createStudy(
            @PathVariable Long id, @RequestBody StudyRequest studyRequest
            ){
        User user = authService.getLoginUser();
        Study createdStudy = studyService.createStudy(studyRequest, id, user);
        return new ApiResponse<>(201, StudyResponse.from(createdStudy));
    }

    @PatchMapping("/{studyId}")
    @Operation(summary = "스터디 일정 수정")
    public ApiResponse<StudyResponse> updateStudy(
            @PathVariable Long id,
            @PathVariable Long studyId,
            @RequestBody StudyUpdateRequest studyUpdateRequest){
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(id);
        Study updatedStudy = studyService.updateStudy(studyId, studyUpdateRequest, user, studyGroup);
        return new ApiResponse<>(200, StudyResponse.from(updatedStudy));
    }

    @DeleteMapping("/{studyId}")
    @Operation(summary = "스터디 일정 삭제")
    public ApiResponse<MessageResponse> deleteStudy(@PathVariable Long id, @PathVariable Long studyId){
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(id);
        boolean isUserInGroup = userGroupRepository.existsByUserAndStudyGroup(user, studyGroup);
        if (!isUserInGroup) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }
        studyService.deleteStudy(studyId);
        return new ApiResponse<>(204,MessageResponse.from());
    }

    @GetMapping("/{date}")
    @Operation(summary = "스터디 일정 가져오기")
    public ApiResponse<StudyResponse> getStudyInfo(
            @PathVariable Long id,
            @PathVariable LocalDate date){
        User user = authService.getLoginUser();
        Study study = studyService.getStudyByGroupDate(id,date);
        return new ApiResponse<>(200, StudyResponse.from(study));

    }

    @GetMapping("") // 연도, 월별 스터디 조화
    @Operation(summary = "스터디 그룹 연도,월별 조회", description = "using groupId")
    public ApiResponse<StudyMonthlyResponse> getGroupDetailsMonthly(
            @Parameter(description = "ID of the group to fetch details", required = true)
            @PathVariable(name = "id") Long id,
            @RequestParam("year") String year,
            @RequestParam("month") String month) {
        StudyMonthlyResponse response = studyService.getStudyByYearMonth(id,year,month);
        return new ApiResponse<>(200, response);
    }
}
