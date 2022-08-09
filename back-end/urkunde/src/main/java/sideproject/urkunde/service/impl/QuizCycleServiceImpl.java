package sideproject.urkunde.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.urkunde.domain.Quiz;
import sideproject.urkunde.domain.QuizCheck;
import sideproject.urkunde.domain.QuizCycle;
import sideproject.urkunde.domain.enums.QuizState;
import sideproject.urkunde.dto.response.QuizCycleResultResponse;
import sideproject.urkunde.dto.response.QuizCycleStartResponse;
import sideproject.urkunde.dto.response.QuizResponseDto;
import sideproject.urkunde.repository.QuizCycleRepository;
import sideproject.urkunde.repository.QuizRepository;
import sideproject.urkunde.service.QuizCycleService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizCycleServiceImpl implements QuizCycleService {

    private final QuizRepository quizRepository;
    private final QuizCycleRepository quizCycleRepository;

    @Override
    @Transactional
    public QuizCycleStartResponse startQuizCycle() {

        // 모든 퀴즈 조회
        List<Quiz> quizzes = quizRepository.findAll();

        List<QuizResponseDto> quizResponseDtos = quizzes.stream().map(quiz -> QuizResponseDto.builder()
                .id(quiz.getId())
                .content(quiz.getContent())
                .answer(quiz.getAnswer()).build()).collect(Collectors.toList());

        //퀴즈 사이클 시작하기 위해 시작시간 DB에 넣어주기 -> 준영속 상태
        QuizCycle quizCycle = QuizCycle.builder()
                .startTime(LocalDateTime.now()).build();

        // 퀴즈 사이클이 가지고 있는 quizList에 quizCheck를 하나 하나 만들어서 넣어주기
        quizCycle.getQuizCheckList().addAll(quizzes.stream()
                .map(quiz -> QuizCheck.builder()
                        .quiz(quiz)
                        .quizCycle(quizCycle)
                        .quizState(QuizState.NONE)
                        .build())
                .collect(Collectors.toList()));

        // quizCycle를 저장하면서, quizCheck도 저장해주기
        QuizCycle savedQuizCycle = quizCycleRepository.save(quizCycle);

        return new QuizCycleStartResponse(savedQuizCycle.getId(), quizResponseDtos);
    }

    @Override
    @Transactional
    public QuizCycleResultResponse endQuizCycle(Long cycleId) {

        QuizCycle quizCycle = quizCycleRepository.findById(cycleId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 퀴즈 사이클입니다."));

        // dirty check
        quizCycle.setEndTime();

        // cycle id를 통해서 QuizCheck 모든 데이터 조회
        int correctCount = 0;
        int incorrectCount = 0;
        int noneCount = 0;

        for(QuizCheck quizCheck : quizCycle.getQuizCheckList()) {
            if(quizCheck.getQuizState() == QuizState.CORRECT) {
                correctCount++;
            } else if(quizCheck.getQuizState() == QuizState.INCORRECT) {
                incorrectCount++;
            } else {
                noneCount++;
            }
        }

       return new QuizCycleResultResponse(correctCount, incorrectCount, noneCount);

    }
}
