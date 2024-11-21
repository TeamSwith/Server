package swith.swithServer.domain.task.dto;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.task.entity.Task;
import swith.swithServer.domain.task.entity.TaskStatus;

import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class TaskResponseDto {
    private Long id;
    @Column(length = 50)
    private String content;

//    @Enumerated(EnumType.STRING)
//    private TaskStatus status;

    public static TaskResponseDto from(Task task){
        return TaskResponseDto.builder()
                .id(task.getId())
                .content(task.getContent())
                .build();
    }
    public static List<TaskResponseDto> from(List<Task> task){
        List<TaskResponseDto> taskResponseDtoList = new ArrayList<>();
        for(Task tasks : task){
            TaskResponseDto taskResponseDto = TaskResponseDto.builder()
                    .id(tasks.getId())
                    .content(tasks.getContent())
                    .build();
            taskResponseDtoList.add(taskResponseDto);
        }
        return taskResponseDtoList;
    }
}
