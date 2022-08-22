package sideproject.urkunde.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class DayCheck {


    @Id
    @Column(updatable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String dayState; // ture, false, none(기본값, 그날 맞춘 문제가 하나도 없는 경우)

    @PrePersist
    public void prePersist() {
        this.date = LocalDate.now();
    }


    @Builder
    public DayCheck(String dayState) {
        this.dayState = dayState;
    }


    public void updateDayState(String dayState) {
        this.dayState = dayState;
    }
}
