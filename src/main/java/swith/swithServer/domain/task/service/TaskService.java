//package swith.swithServer.domain.task.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import swith.swithServer.domain.task.controller.TaskRequest;
//import swith.swithServer.domain.task.domain.Task;
//import swith.swithServer.domain.task.domain.TaskStatus;
//import swith.swithServer.domain.task.repository.TaskRepository;
//
//@Service
//public class TaskService {
//
//    @Autowired
//    private TaskRepository taskRepository;
//
//    /**
//     * 새로운 과제를 생성
//     */
//    public Task createTask(TaskRequest request) {
//        // 과제 생성
//        Task task = new Task(
//                request.getContent(),
//                TaskStatus.PENDING // 기본 상태를 PENDING으로 설정
//        );
//        return taskRepository.save(task);
//    }
//}