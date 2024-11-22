package swith.swithServer.domain.studyGroup.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class GroupCreateRequest {
//    private String groupId;
    private String groupInsertId;
    private String groupPw;
    private String groupName = "groupName";      // Optional: 그룹 이름
    private Long maxNum = 10L;       // Default 값
    private Long memberNum = 0L;     // Default 값
    private String subject = "0";   // Default 값
    private Long period = 1L;        // Default 값
    private String communication = "0"; // Default 값
}