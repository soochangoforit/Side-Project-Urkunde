package sideproject.urkunde.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuizCheckResponse {

    @ApiModelProperty(value = "퀴즈 정답 여부")
    private String isCorrect;

}
