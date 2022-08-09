package sideproject.urkunde.domain.enums;

import lombok.Getter;

@Getter
public enum QuizState {

    CORRECT("correct"),
    INCORRECT("incorrect"),
    NONE("none");

    private final String state;
    QuizState(String state) {
        this.state = state;
    }
}
