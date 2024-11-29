package swith.swithServer.domain.task.dto;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.usertask.entity.TaskStatus;
import swith.swithServer.domain.usertask.entity.UserTask;
import swith.swithServer.domain.usertask.repository.UserTaskRepository;
import swith.swithServer.domain.usertask.service.UserTaskService;
import swith.swithServer.global.error.ErrorCode;
import swith.swithServer.global.error.exception.BusinessException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@Builder
@AllArgsConstructor
public class TaskResponse {

    private static UserTaskService userTaskService;

    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TaskStatus taskStatus;
    @Column(length = 50)
    private String content;

    public static TaskResponse from(Task task, UserTask userTask){

        return TaskResponse.builder()
                .id(task.getId())
                .taskStatus(userTask.getTaskStatus())
                .content(task.getContent())
                .build();
    }

    public static List<TaskResponse> from(List<Task> task, List<UserTask> userTasks){
        List<TaskResponse> taskResponseList = new ArrayList<>();
        int i=0;
        for(Task tasks : task){
            if(userTasks.get(i)!=null) {
                TaskResponse taskResponse = TaskResponse.builder()
                        .id(tasks.getId())
                        .taskStatus(userTasks.get(i).getTaskStatus())
                        .content(tasks.getContent())
                        .build();
                i++;
                taskResponseList.add(taskResponse);
            }
            else
                i++;
        }
        return taskResponseList;
    }
}
