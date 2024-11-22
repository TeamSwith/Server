package swith.swithServer.domain.study.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.study.dto.MessageResponse;
import swith.swithServer.domain.study.dto.StudyRequest;
import swith.swithServer.domain.study.dto.StudyResponse;
import swith.swithServer.domain.study.dto.StudyUpdateRequest;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.global.response.ApiResponse;
@RestController
@RequestMapping("/group/{id}/study")
@RequiredArgsConstructor
@Tag(name="날짜 별 스터디")
public class StudyController {
    private final StudyService studyService;
    private final GroupService groupService;

    @PostMapping
    @Operation(summary="스터디 일정 생성")
    public ApiResponse<StudyResponse> createStudy(
            @PathVariable Long id, @RequestBody StudyRequest studyRequest
            ){
        Study createdStudy = studyService.createStudy(studyRequest, id);
        return new ApiResponse<>(201, StudyResponse.from(createdStudy));
    }

    @PatchMapping("/{studyId}")
    @Operation(summary = "스터디 일정 수정")
    public ApiResponse<StudyResponse> updateStudy(
            @PathVariable Long id,
            @PathVariable Long studyId,
            @RequestBody StudyUpdateRequest studyUpdateRequest){
        StudyGroup studyGroup = groupService.getGroupById(id);
        Study updatedStudy = studyService.updateStudy(studyId, studyUpdateRequest);
        return new ApiResponse<>(200, StudyResponse.from(updatedStudy));
    }

    @DeleteMapping("/{studyId}")
    @Operation(summary = "스터디 일정 삭제")
    public ApiResponse<MessageResponse> deleteStudy(@PathVariable Long id, @PathVariable Long studyId){
        StudyGroup studyGroup = groupService.getGroupById(id);
        studyService.deleteStudy(studyId);
        return new ApiResponse<>(204,MessageResponse.from());
    }

    @GetMapping("/{studyId}")
    @Operation(summary = "스터디 일정 가져오기")
    public ApiResponse<StudyResponse> getStudyInfo(@PathVariable Long id, @PathVariable Long studyId){
        StudyGroup studyGroup = groupService.getGroupById(id);
        Study study = studyService.getStudyById(studyId);
        return new ApiResponse<>(200, StudyResponse.from(study));

    }
}
