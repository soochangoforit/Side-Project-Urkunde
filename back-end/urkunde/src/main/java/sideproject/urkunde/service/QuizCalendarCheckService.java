package sideproject.urkunde.service;

import sideproject.urkunde.dto.request.QuizCalendarCheck;

import java.time.LocalDate;
import java.util.Map;

public interface QuizCalendarCheckService {


    Map<LocalDate, String> findCycleIdBetweenDates(QuizCalendarCheck quizCalendarCheck);
}
