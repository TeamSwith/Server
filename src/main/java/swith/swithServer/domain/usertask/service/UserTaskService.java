//package swith.swithServer.domain.usertask.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import swith.swithServer.domain.usertask.domain.UserTaskDomain;
//import swith.swithServer.domain.usertask.dto.UserTaskDTO;
//import swith.swithServer.domain.usertask.repository.UserTaskRepository;
//import swith.swithServer.domain.group.domain.GroupDomain;
//import swith.swithServer.domain.task.domain.Task;
//import swith.swithServer.domain.user.domain.User;
//
//@Service
//@RequiredArgsConstructor
//public class UserTaskService {
//
//    private final UserTaskRepository repository;
//
//    public UserTaskDomain createUserTask(UserTaskDto dto) {
//        UserTaskDomain userTask = UserTaskDomain.builder()
//                .user(new User(dto.getUserId())) // User 객체는 id만 설정
//                .task(new Task(dto.getTaskId())) // Task 객체는 id만 설정
//                .group(new GroupDomain(dto.getGroupId())) // Group 객체는 id만 설정
//                .status(UserTaskDomain.TaskStatus.valueOf(dto.getStatus().toUpperCase()))
//                .build();
//
//        return repository.save(userTask);
//    }
//
//    public UserTaskDomain updateUserTaskStatus(Long id, String status) {
//        UserTaskDomain userTask = repository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("UserTask not found"));
//
//        userTask.setStatus(UserTaskDomain.TaskStatus.valueOf(status.toUpperCase()));
//        return repository.save(userTask);
//    }
//}