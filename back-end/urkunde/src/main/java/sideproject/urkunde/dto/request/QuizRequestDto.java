package sideproject.urkunde.dto.request;

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
    private String content;

    @NotEmpty(message = "정답을 입력해주세요.")
    private String answer;
}
