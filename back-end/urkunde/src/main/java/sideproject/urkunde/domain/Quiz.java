package sideproject.urkunde.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sideproject.urkunde.dto.request.QuizRequestDto;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter @NoArgsConstructor
public class Quiz extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @Column(nullable = false)
    private String content;

    @NotEmpty
    @Column(nullable = false)
    private String answer;


    @Builder
    public Quiz(String content, String answer) {
        this.content = content;
        this.answer = answer;
    }

    public static Quiz toQuizEntity(QuizRequestDto quizCreateDto) {
        return Quiz.builder()
                .content(quizCreateDto.getContent())
                .answer(quizCreateDto.getAnswer())
                .build();
    }

    public Quiz toUpdateQuizEntity(QuizRequestDto quizUpdateDto) {
        this.content = quizUpdateDto.getContent();
        this.answer = quizUpdateDto.getAnswer();

        return this;
    }

}
