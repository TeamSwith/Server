package swith.swithServer.domain.group.dto;

import lombok.*;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@Data

public class GroupUpdateRequest {
    @NotNull
    private String groupName;

    @NotNull
    private int maxNum;

    @NotNull
    private String subject;

    @NotNull
    private int period;

    @NotNull
    private String communication;
}