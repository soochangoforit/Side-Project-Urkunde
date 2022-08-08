package sideproject.urkunde.dto.request;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

/**
 * 퀴즈 생성, 수정시 활용
 */
@NoArgsConstructor @Setter @Getter
public class QuizRequestDto {

    @NotEmpty(message = "문제를 입력해주세요.")
    @ApiModelProperty(value = "퀴즈 질문", example = "오늘은 무슨 요일이죠?", required = true)
    private String content;

    @NotEmpty(message = "정답을 입력해주세요.")
    @ApiModelProperty(value = "퀴즈 정답", example = "월요일" , required = true)
    private String answer;
}
