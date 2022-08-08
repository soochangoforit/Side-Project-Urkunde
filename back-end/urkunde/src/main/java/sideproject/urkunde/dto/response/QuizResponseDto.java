package sideproject.urkunde.dto.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import sideproject.urkunde.domain.Quiz;


@Getter
public class QuizResponseDto {

    @ApiModelProperty(value = "퀴즈 고유 아이디")
    private Long id;

    @ApiModelProperty(value = "퀴즈 질문")
    private String content;

    @ApiModelProperty(value = "퀴즈 정답")
    private String answer;

    @Builder
    public QuizResponseDto(Long id, String content, String answer) {
        this.id = id;
        this.content = content;
        this.answer = answer;
    }

    public static QuizResponseDto toDto(Quiz quiz){
        return QuizResponseDto.builder()
                .id(quiz.getId())
                .content(quiz.getContent())
                .answer(quiz.getAnswer())
                .build();
    }

}
