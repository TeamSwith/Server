package swith.swithServer.domain.group.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GroupResponse {
    private String groupName;
    private String groupInsertId;
    private int maxNum;
    private String subject;
    private int period;
    private String communication;
}