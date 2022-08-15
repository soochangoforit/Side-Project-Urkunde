package sideproject.urkunde.service;

import sideproject.urkunde.domain.Quiz;
import sideproject.urkunde.dto.request.QuizRequestDto;
import sideproject.urkunde.dto.response.QuizResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface QuizService {

     Quiz createQuiz(QuizRequestDto quiz);

     //update quiz
     Quiz updateQuiz(Long id, QuizRequestDto quiz);

     Quiz findById(Long id);

     Quiz findByContent(String content);

     void deleteById(Long id);

     List<QuizResponseDto> retrieveAllQuizs();

    int updateCreateDateFromNow(LocalDateTime now);

     void deleteAll();

}
