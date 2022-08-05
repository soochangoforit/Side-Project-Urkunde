package sideproject.urkunde.dto.response;

import lombok.*;


@Getter
public class QuizResponseDto {

    private Long id;

    private String content;

    private String answer;

    @Builder
    public QuizResponseDto(Long id, String content, String answer) {
        this.id = id;
        this.content = content;
        this.answer = answer;
    }

}
