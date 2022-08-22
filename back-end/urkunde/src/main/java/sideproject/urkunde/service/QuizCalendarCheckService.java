package sideproject.urkunde.service;

import sideproject.urkunde.dto.request.QuizCalendarCheck;
import sideproject.urkunde.dto.response.WeekCheckResponse;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

public interface QuizCalendarCheckService {


    WeekCheckResponse findCycleIdBetweenDates(QuizCalendarCheck quizCalendarCheck);

    WeekCheckResponse findStatusBetweenDates(QuizCalendarCheck quizCalendarCheck);
}
