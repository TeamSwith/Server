package swith.swithServer.domain.study.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.study.entity.Study;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class StudyResponseDto {
    private Long id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "12:00:00")
    private LocalTime time;
    private String location;

    public static StudyResponseDto from(Study study){
        return StudyResponseDto.builder()
                .id(study.getId())
                .date(study.getDate())
                .time(study.getTime())
                .location(study.getLocation())
                .build();
    }
}
