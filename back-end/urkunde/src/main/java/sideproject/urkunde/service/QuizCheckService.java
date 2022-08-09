package sideproject.urkunde.service;

import sideproject.urkunde.dto.request.QuizCheckRequest;
import sideproject.urkunde.dto.response.QuizCheckResponse;

public interface QuizCheckService {
    QuizCheckResponse checkQuiz(Long quizId , QuizCheckRequest quizCheckRequest);
}
