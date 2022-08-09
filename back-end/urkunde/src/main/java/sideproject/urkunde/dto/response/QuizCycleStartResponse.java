package sideproject.urkunde.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class QuizCycleStartResponse {

    @ApiModelProperty(value = "퀴즈에 할당된 사이클 아이디")
    private Long cycleId;

    @ApiModelProperty(value = "퀴즈 사이클에 할당된 퀴즈 리스트")
    private List<QuizResponseDto> quizList;

}
