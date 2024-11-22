package swith.swithServer.domain.studyGroup.dto;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotNull;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

@Getter
@Setter
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

    // 변경된 부분: StudyGroup 업데이트 처리
    public void applyTo(StudyGroup studyGroup) {
        studyGroup.setGroupName(this.groupName);
        studyGroup.setMaxNum(this.maxNum);
        studyGroup.setSubject(this.subject);
        studyGroup.setPeriod(this.period);
        studyGroup.setCommunication(this.communication);
    }
}