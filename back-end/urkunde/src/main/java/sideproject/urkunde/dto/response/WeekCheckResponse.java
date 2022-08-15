package sideproject.urkunde.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

@Getter
@AllArgsConstructor
public class WeekCheckResponse {

    private String sunday = "none";
    private String monday = "none";
    private String tuesday = "none";
    private String wednesday = "none";
    private String thursday = "none";
    private String friday = "none";
    private String saturday = "none";


    @Builder
    public WeekCheckResponse(Map<DayOfWeek, String> quizMap) {
        this.sunday = quizMap.get(DayOfWeek.SUNDAY) == null ? "none" : quizMap.get(DayOfWeek.SUNDAY);
        this.monday = quizMap.get(DayOfWeek.MONDAY) == null ? "none" : quizMap.get(DayOfWeek.MONDAY);
        this.tuesday = quizMap.get(DayOfWeek.TUESDAY) == null ? "none" : quizMap.get(DayOfWeek.TUESDAY);
        this.wednesday = quizMap.get(DayOfWeek.WEDNESDAY) == null ? "none" : quizMap.get(DayOfWeek.WEDNESDAY);
        this.thursday = quizMap.get(DayOfWeek.THURSDAY) == null ? "none" : quizMap.get(DayOfWeek.THURSDAY);
        this.friday = quizMap.get(DayOfWeek.FRIDAY) == null ? "none" : quizMap.get(DayOfWeek.FRIDAY);
        this.saturday = quizMap.get(DayOfWeek.SATURDAY) == null ? "none" : quizMap.get(DayOfWeek.SATURDAY);

    }

}
