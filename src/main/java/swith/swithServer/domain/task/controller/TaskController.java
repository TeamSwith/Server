package swith.swithServer.domain.task.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import swith.swithServer.domain.study.dto.MessageResponse;
import swith.swithServer.domain.study.entity.Study;
import swith.swithServer.domain.study.service.StudyService;
import swith.swithServer.domain.studyGroup.dto.StringRequest;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;
import swith.swithServer.domain.studyGroup.service.GroupService;
import swith.swithServer.domain.task.dto.TaskResponse;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.task.service.TaskService;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.userGroup.repository.UserGroupRepository;
import swith.swithServer.domain.usertask.entity.UserTask;
import swith.swithServer.domain.usertask.service.UserTaskService;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;
import swith.swithServer.global.oauth.service.OauthService;
import swith.swithServer.global.response.ApiResponse;

import java.util.List;

@RestController
@RequestMapping("/group/{id}/study/{studyId}")
@RequiredArgsConstructor
@Tag(name="과제")
public class TaskController {
    private final TaskService taskService;
    private final StudyService studyService;
    private final GroupService groupService;
    private final OauthService authService;
    private final UserTaskService userTaskService;
    private final UserGroupRepository userGroupRepository;


    @PostMapping("/create")
    @Operation(summary = "과제 생성")
    public ApiResponse<TaskResponse> createTask(@PathVariable Long id, @PathVariable Long studyId, @RequestBody StringRequest stringRequest){
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(id);
        Task createdTask = taskService.createTask(studyGroup, studyId, stringRequest, user);
        UserTask userTask = userTaskService.getUserTaskByUserAndTask(user, createdTask);
        return new ApiResponse<>(201, TaskResponse.from(createdTask, userTask));
    }

    @GetMapping("/get")
    @Operation(summary = "과제 가져오기")
    public ApiResponse<List<TaskResponse>> getTask(@PathVariable Long id, @PathVariable Long studyId){
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(id);
        Study study = studyService.getStudyById(studyId);
        List<Task> task = taskService.getTaskByStudy(study);
        List<UserTask> userTasks = userTaskService.getUserTaskByUserAndTaskList(user,task);
        return new ApiResponse<>(200, TaskResponse.from(task, userTasks));

    }
    @DeleteMapping("/{taskId}")
    @Operation(summary = "과제 삭제")
    public ApiResponse<MessageResponse> deleteTask(@PathVariable Long id, @PathVariable Long studyId, @PathVariable Long taskId){
        User user = authService.getLoginUser();
        StudyGroup studyGroup = groupService.getGroupById(id);
        boolean isUserInGroup = userGroupRepository.existsByUserAndStudyGroup(user, studyGroup);
        if (!isUserInGroup) {
            throw new BusinessException(ErrorCode.USER_NOT_IN_GROUP);
        }
        Study study = studyService.getStudyById(studyId);
        taskService.deleteTask(taskId);
        return new ApiResponse<>(204, MessageResponse.from());
    }
}
