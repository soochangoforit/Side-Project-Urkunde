package sideproject.urkunde.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class QuizCheckRequest {

    @NotNull(message = "퀴즈에 해당하는 사이클을 입력해주세요.")
    @ApiModelProperty(value = "퀴즈 사이클 고유 아이디")
    private Long cycleId;

    @NotBlank(message = "정답을 입력해주세요.")
    @ApiModelProperty(value = "사용자가 제출한 퀴즈 답안")
    private String clientAnswer;
}
