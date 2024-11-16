package swith.swithServer.domain.study.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.response.ApiResponse;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/group/{id}/study")
@RequiredArgsConstructor

public class StudyController {
    private final StudyService studyService;

    //study 생성 (날짜, groupId 필요)
/*    @PostMapping
    public ResponseEntity<Study> createStudy(@RequestBody Study study){
        Study createdStudy = studyService.createStudy(study);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudy);
    }
  */

    @PostMapping
    public ApiResponse<Study> createStudy(
            @PathVariable Long id,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time,
            @RequestParam String location){
        Study createdStudy = studyService.createStudy(date, time, location, id);
        //return ResponseEntity.status(HttpStatus.CREATED).body(createdStudy);
        return new ApiResponse<>(201,createdStudy);
    }

    //study 수정
    @PatchMapping("/{studyId}")
    public ApiResponse<Study> updateStudy(
            @PathVariable Long studyId,
            @RequestParam LocalTime time,
            @RequestParam String location){
        Study updatedStudy = studyService.updateStudy(studyId, time, location);
        //return ResponseEntity.ok().body(updatedStudy);
        return new ApiResponse<>(200, updatedStudy);
    }

    //study 삭제
    @DeleteMapping("/{studyId}")
    public ApiResponse<Void> deleteStudy(@PathVariable Long studyId){
        studyService.deleteStudy(studyId);
        //return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        return new ApiResponse<>(204,null);
    }

    //study 시간, 위치 보여주기
    @GetMapping("/{studyId}")
    public ApiResponse<Map<String, Object>> getStudyInfo(@PathVariable Long studyId){
        Study study = studyService.getStudyById(studyId)
                .orElseThrow(()-> new BusinessException(ErrorCode.STUDY_DOESNT_EXIST));

        Map<String, Object> response = new HashMap<>();
        //response.put("time", study.getTime());
        //response.put("location", study.getLocation());
        //테스트 용!
        response.put("time", "12:00:00");
        response.put("location", "숭실둥실대학교");
        //return ResponseEntity.ok(response);
        return new ApiResponse<>(200, response);

    }

}
