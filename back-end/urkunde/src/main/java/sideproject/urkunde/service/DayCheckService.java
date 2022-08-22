package sideproject.urkunde.service;

import sideproject.urkunde.domain.DayCheck;

import java.time.LocalDate;
import java.util.Optional;

public interface DayCheckService {

    void updateDayCheck(LocalDate now);

    Optional<DayCheck> findStatusOfDay(LocalDate localDate);
}
