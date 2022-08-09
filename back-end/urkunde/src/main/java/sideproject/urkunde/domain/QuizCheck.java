package sideproject.urkunde.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import sideproject.urkunde.domain.enums.QuizState;

import javax.persistence.*;

@Entity
@Getter @NoArgsConstructor
@IdClass(QuizIdAndQuizCycleId.class)
public class QuizCheck {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id" , nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Quiz quiz;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_cycle_id" , nullable = false)
    private QuizCycle quizCycle;

    @Enumerated(EnumType.STRING)
    private QuizState quizState;

    @PrePersist
    public void prePersist() {
        this.quizState = this.quizState == null ? QuizState.NONE : this.quizState;
    }


    @Builder
    public QuizCheck(Quiz quiz, QuizCycle quizCycle, QuizState quizState) {
        this.quiz = quiz;
        this.quizCycle = quizCycle;
        this.quizState = quizState;
    }

}
