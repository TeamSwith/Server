package swith.swithServer.domain.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.domain.task.dto.TaskResponseDto;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.task.service.TaskService;
import swith.swithServer.global.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/group/{id}/study/{studyId}")
@RequiredArgsConstructor
@Tag(name="과제")
public class TaskController {
    private final TaskService taskService;
    private final StudyService studyService;


    @PostMapping("/create")
    @Operation(summary = "과제 생성")
    public ApiResponse<TaskResponseDto> createTask(@PathVariable Long studyId, @RequestParam String content){
        Task createdTask = taskService.createTask(studyId, content);
        return new ApiResponse<>(201, TaskResponseDto.from(createdTask));
    }

    @GetMapping("/get")
    @Operation(summary = "과제 가져오기")
    public ApiResponse<List<TaskResponseDto>> getTask(@PathVariable Long studyId){
        Study study = studyService.getStudyById(studyId);
        List<Task> task = taskService.getTaskByStudy(study);
        return new ApiResponse<>(200, TaskResponseDto.from(task));

    }
    @DeleteMapping("/{taskId}")
    @Operation(summary = "과제 삭제")
    public ApiResponse<Void> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return new ApiResponse<>(204,null);
    }
}