package sideproject.urkunde.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@NoArgsConstructor
public class QuizCalendarCheck {

    @NotBlank(message = "주간 체크를 위한 오늘 날짜를 '2022-08-14' 형식으로 넣어주세요")
    @ApiModelProperty(value = "주간 체크를 위한 오늘 날짜를 '2022-08-14' 형식으로 넣어주세요")
    private String recentDate; // 현재 마무리 요일

    @NotBlank(message = "주간 단위로 시작하고자 하는 일요일 날짜를 '2022-08-14' 형식으로 넣어주세요")
    @ApiModelProperty(value = "주간 단위로 시작하고자 하는 일요일 날짜를 '2022-08-14' 형식으로 넣어주세요")
    private String startDate; // 시작 일요일


    public List<LocalDate> datesBetweenStartDateToRecentDate() {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(recentDate, DateTimeFormatter.ISO_DATE);
        List<LocalDate> dates = Stream.iterate(start, date -> date.plusDays(1))
                .limit(end.toEpochDay() - start.toEpochDay() + 1)
                .collect(Collectors.toList());
        return dates;
    }

}
