package swith.swithServer.domain.task.dto;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.task.entity.Task;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class TaskResponse {
    private Long id;
    @Column(length = 50)
    private String content;

    public static TaskResponse from(Task task){
        return TaskResponse.builder()
                .id(task.getId())
                .content(task.getContent())
                .build();
    }
    public static List<TaskResponse> from(List<Task> task){
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
