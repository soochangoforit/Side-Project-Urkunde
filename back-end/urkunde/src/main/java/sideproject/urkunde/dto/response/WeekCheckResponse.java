package sideproject.urkunde.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Map;

@Getter
@AllArgsConstructor
@Builder
public class WeekCheckResponse {

    @ApiModelProperty(value = "일요일부터 현재 날짜까지 퀴즈 맞춘 상태를 반환한다. ture: 다 풀었을 경우, false : 덜 풀었을 경우" +
            " none : 퀴즈 자체를 풀지 않는 경우" , example = "'2022-08-14' : 'ture'")
    Map<LocalDate,String> weekChecks;

}
