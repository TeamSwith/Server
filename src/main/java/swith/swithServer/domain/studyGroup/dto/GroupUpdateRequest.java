package swith.swithServer.domain.studyGroup.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GroupUpdateRequest {
    @NotNull
    private String groupName;

    @NotNull
    private Long maxNum;

    @NotNull
    private String subject;

    @NotNull
    private Long period;

    @NotNull
    private String communication;

    public void applyTo(StudyGroup studyGroup) {
        studyGroup.updateGroupDetails(
                this.groupName,
                this.maxNum,
                this.subject,
                this.period,
                this.communication
        );
    }
}