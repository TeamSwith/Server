package swith.swithServer.domain.group.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
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