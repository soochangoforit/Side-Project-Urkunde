package sideproject.urkunde.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizCycleResultResponse {

    @ApiModelProperty(value = "퀴즈 정답 개수")
    private int correctCount;
    @ApiModelProperty(value = "퀴즈 오답 개수")
    private int incorrectCount;
    @ApiModelProperty(value = "퀴즈 풀지 않는 개수")
    private int noneCount;
}
