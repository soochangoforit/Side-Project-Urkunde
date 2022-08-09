package sideproject.urkunde.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class QuizCycleEndRequest {

    @ApiModelProperty(value = "종료하고자 하는 사이클 아이디")
    @NotNull(message = "종료하고자 하는 사이클 아이디를 입력해주세요.")
    private Long cycleId;
}
