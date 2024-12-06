package swith.swithServer.domain.study.dto;

import lombok.Builder;
import lombok.Getter;
import swith.swithServer.domain.studyGroup.entity.StudyGroup;

import java.util.List;

@Getter
@Builder
public class StudyMonthlyResponse {
    private Long id;
    private String year;
    private String month;
    private List<Integer> dayList;

    public static StudyMonthlyResponse from(StudyGroup studyGroup, String year, String month,List<Integer> dayList){
        return StudyMonthlyResponse.builder()
                .id(studyGroup.getId())
                .year(year)
                .month(month)
                .dayList(dayList)
                .build();
    }

}
