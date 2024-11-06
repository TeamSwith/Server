package swith.swithServer.domain.Study.Controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.Study.Entity.Study;
import swith.swithServer.domain.Study.service.StudyService;

import java.time.LocalDateTime;
@RestController
@RequestMapping("/주소~~")
@RequiredArgsConstructor
public class StudyController {
    private final StudyService studyService;


    //study 생성
    @PostMapping
    public ResponseEntity<Study> createStudy(@RequestBody Study study){
        Study createdStudy = studyService.createStudy(study);
        return ResponseEntity.ok(createdStudy);
    }


    //study 수정
    @PutMapping("/{id}")
    public ResponseEntity<Study> updateStudy(
            @PathVariable Long id,
            @RequestParam LocalDateTime time,
            @RequestParam String location){
        Study updatedStudy = studyService.updateStudy(id, time, location);
        return ResponseEntity.ok(updatedStudy);
    }


    //study 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudy(@PathVariable Long id){
        studyService.deleteStudy(id);
        return ResponseEntity.noContent().build();
    }

}
