package swith.swithServer.domain.studyGroup.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import lombok.Builder;

@Getter
@Setter
//@ToString
//@EqualsAndHashCode
@Builder
public class GroupResponse {
    private String groupName;
    private String groupInsertId;
    private Long maxNum;
    private String subject;
    private Long period;
    private String communication;
}