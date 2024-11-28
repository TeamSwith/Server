package swith.swithServer.domain.task.dto;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.user.entity.User;
import swith.swithServer.domain.usertask.entity.TaskStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class TaskResponse {
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private TaskStatus taskStatus;
    @Column(length = 50)
    private String content;

    public static TaskResponse from(Task task, User user){
        return TaskResponse.builder()
                .id(task.getId())
                .content(task.getContent())
                .build();
    }
    public static List<TaskResponse> from(List<Task> task, User user){
        List<TaskResponse> taskResponseList = new ArrayList<>();
        for(Task tasks : task){
            TaskResponse taskResponse = TaskResponse.builder()
                    .id(tasks.getId())
                    .content(tasks.getContent())
                    .build();
            taskResponseList.add(taskResponse);
        }
        return taskResponseList;
    }
}
