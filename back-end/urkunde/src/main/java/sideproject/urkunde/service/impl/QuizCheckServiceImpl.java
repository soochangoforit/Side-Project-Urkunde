package sideproject.urkunde.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.urkunde.domain.Quiz;
import sideproject.urkunde.domain.QuizCheck;
import sideproject.urkunde.domain.enums.QuizState;
import sideproject.urkunde.dto.request.QuizCheckRequest;
import sideproject.urkunde.dto.response.QuizCheckResponse;
import sideproject.urkunde.repository.QuizCheckRepository;
import sideproject.urkunde.repository.QuizRepository;
import sideproject.urkunde.service.QuizCheckService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuizCheckServiceImpl implements QuizCheckService {

    private final QuizRepository quizRepository;
    private final QuizCheckRepository quizCheckRepository;
    @Override
    @Transactional
    public QuizCheckResponse checkQuiz(Long quizId , QuizCheckRequest quizCheckRequest) {

        // 해당 quizId로 퀴즈를 조회해서 맞는지 확인
        Quiz quiz = quizRepository.findById(quizId).orElseThrow(() -> new IllegalArgumentException("해당 퀴즈가 없습니다."));

        // QuizCheckRequest Argument check
        if(quizCheckRequest.getCycleId() == null) {
            throw new IllegalArgumentException("퀴즈 사이클 아이디를 입력해주세요.");
        }
        if (quizCheckRequest.getClientAnswer() == null) {
            throw new IllegalArgumentException("퀴즈에 대한 답변을 작성해주세요.");
        }

        QuizState quizState = null;

        if(quiz.getAnswer().equals(quizCheckRequest.getClientAnswer())){
            quizState = QuizState.CORRECT;
        }else{
            quizState = QuizState.INCORRECT;
        }

        // 결과에 따라서 quizCheck update
        QuizCheck quizCheck = quizCheckRepository.findByQuizAndQuizCycle(quiz.getId(), quizCheckRequest.getCycleId())
                .orElseThrow(() -> new IllegalArgumentException("해당 퀴즈 사이클에 해당하는 문제가 없습니다."));

        // dirty check으로 상태 변화
        quizCheck.setQuizState(quizState);

        return new QuizCheckResponse(quizState.getState());
    }
}
