package swith.swithServer.domain.study.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalTime;

@AllArgsConstructor
@Getter
public class StudyUpdateRequest {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @Schema(type = "string", pattern = "HH:mm:ss", example = "12:00:00")
    private LocalTime time;
    private String location;
}