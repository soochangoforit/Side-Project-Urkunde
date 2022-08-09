package sideproject.urkunde.domain.enums;

import lombok.Getter;

@Getter
public enum QuizState {

    SUCCESS("success"),
    FAIL("fail"),
    NONE("none");

    private String state;
    QuizState(String state) {
        this.state = state;
    }
}
