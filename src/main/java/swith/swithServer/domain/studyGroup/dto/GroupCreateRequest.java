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
    private int maxNum = 10;       // Default 값
    private int memberNum = 0;     // Default 값
    private String subject = "0";   // Default 값
    private int period = 1;        // Default 값
    private String communication = "0"; // Default 값
}