package sideproject.urkunde.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class QuizIdAndQuizCycleId implements Serializable {
    private Long quizId;
    private Long quizCycleId;
}
