package swith.swithServer.domain.Study.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.Study.Entity.Study;
import swith.swithServer.domain.Study.service.StudyService;

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
    public ResponseEntity<Study> createStudy(
            @PathVariable Long id,
            @RequestParam LocalDate date,
            @RequestParam LocalTime time,
            @RequestParam String location){
        Study createdStudy = studyService.createStudy(date, time, location, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdStudy);
    }

    //study 수정
    @PatchMapping("/{studyId}")
    public ResponseEntity<Study> updateStudy(
            @PathVariable Long studyId,
            @RequestParam LocalTime time,
            @RequestParam String location){
        Study updatedStudy = studyService.updateStudy(studyId, time, location);
        return ResponseEntity.ok().body(updatedStudy);
    }

    //study 삭제
    @DeleteMapping("/{studyId}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long studyId){
        studyService.deleteStudy(studyId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    //study 시간, 위치 보여주기
    @GetMapping("/{studyId}")
    public ResponseEntity<Map<String, Object>> getStudyInfo(@PathVariable Long studyId){
        Optional<Study> studyOptional = studyService.getStudyById(studyId);

        if(studyOptional.isPresent()){
            Study study=studyOptional.get();
            Map<String, Object> response = new HashMap<>();
            //response.put("time", study.getTime());
            //response.put("location", study.getLocation());
            //테스트 용!
            response.put("time", "12:00:00");
            response.put("location", "숭실둥실대학교");
            return ResponseEntity.ok(response);
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

}
