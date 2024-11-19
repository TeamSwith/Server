//package swith.swithServer.domain.usertask.controller;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import swith.swithServer.domain.usertask.dto.UserTaskDTO;
//import swith.swithServer.domain.usertask.service.UserTaskService;
//
//@RestController
//@RequestMapping("/usertask")
//@RequiredArgsConstructor
//public class UserTaskController {
//
//    private final UserTaskService service;
//
//    @PostMapping
//    public ResponseEntity<?> createUserTask(@RequestBody UserTaskDTO dto) {
//        return ResponseEntity.ok(service.createUserTask(dto));
//    }
//    @PutMapping("/{id}/status")
//    public ResponseEntity<?> updateUserTaskStatus(@PathVariable Long id, @RequestParam String status) {
//        return ResponseEntity.ok(service.updateUserTaskStatus(id, status));
//    }
//}