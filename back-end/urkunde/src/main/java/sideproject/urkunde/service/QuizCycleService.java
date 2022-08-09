package sideproject.urkunde.service;

import sideproject.urkunde.domain.QuizCycle;
import sideproject.urkunde.dto.response.QuizCycleResultResponse;
import sideproject.urkunde.dto.response.QuizCycleStartResponse;

public interface QuizCycleService {
    QuizCycleStartResponse startQuizCycle();

    QuizCycleResultResponse endQuizCycle(Long cycleId);

}
