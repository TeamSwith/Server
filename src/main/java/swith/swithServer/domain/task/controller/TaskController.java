//package swith.swithServer.domain.task.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import swith.swithServer.domain.task.domain.Task;
//import swith.swithServer.domain.task.service.TaskService;
//
//@RestController
//@RequestMapping("/api/task")
//public class TaskController {
//
//    @Autowired
//    private TaskService taskService;
//
//    /**
//     * 과제 생성 API
//     */
//    @PostMapping("/create")
//    public ResponseEntity<Task> createTask(@RequestBody TaskRequest request) {
//        Task task = taskService.createTask(request);
//        return ResponseEntity.ok(task);
//    }
//}