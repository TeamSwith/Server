package swith.swithServer.domain.study.controller;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.study.dto.StudyRequestDto;
import swith.swithServer.domain.study.dto.StudyResponseDto;
import swith.swithServer.domain.study.dto.StudyUpdateDto;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.global.response.ApiResponse;
@RestController
@RequestMapping("/group/{id}/study")
@RequiredArgsConstructor
@Tag(name="날짜 별 스터디")
public class StudyController {
    private final StudyService studyService;


    @PostMapping
    @Operation(summary="스터디 일정 생성")
    public ApiResponse<StudyResponseDto> createStudy(
            @PathVariable Long id, @RequestBody StudyRequestDto studyRequestDto
            ){
        Study createdStudy = studyService.createStudy(studyRequestDto.getDate(), studyRequestDto.getTime(), studyRequestDto.getLocation(), id);
        return new ApiResponse<>(201, StudyResponseDto.from(createdStudy));
    }

    @PatchMapping("/{studyId}")
    @Operation(summary = "스터디 일정 수정")
    public ApiResponse<StudyResponseDto> updateStudy(
            @PathVariable Long studyId,
            @RequestBody StudyUpdateDto studyUpdateDto){
        Study updatedStudy = studyService.updateStudy(studyId, studyUpdateDto.getTime(), studyUpdateDto.getLocation());
        return new ApiResponse<>(200, StudyResponseDto.from(updatedStudy));
    }

    @DeleteMapping("/{studyId}")
    @Operation(summary = "스터디 일정 삭제")
    public ApiResponse<Void> deleteStudy(@PathVariable Long studyId){
        studyService.deleteStudy(studyId);
        return new ApiResponse<>(204,null);
    }

    @GetMapping("/{studyId}")
    @Operation(summary = "스터디 일정 가져오기")
    public ApiResponse<StudyResponseDto> getStudyInfo(@PathVariable Long studyId){
        Study study = studyService.getStudyById(studyId);
        return new ApiResponse<>(200, StudyResponseDto.from(study));

    }

}
