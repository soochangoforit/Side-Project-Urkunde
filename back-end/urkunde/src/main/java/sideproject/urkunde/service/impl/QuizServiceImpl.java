package sideproject.urkunde.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.urkunde.domain.Quiz;
import sideproject.urkunde.dto.request.QuizRequestDto;
import sideproject.urkunde.dto.response.QuizResponseDto;
import sideproject.urkunde.repository.QuizRepository;
import sideproject.urkunde.service.QuizService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;

    @Autowired
    public QuizServiceImpl(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    @Override
    @Transactional
    public Quiz createQuiz(QuizRequestDto dto) {

        // null check from dto
        if (dto.getContent().isEmpty() || dto.getContent() == null) {
            throw new IllegalArgumentException("퀴즈 내용이 없습니다.");
        }

        Quiz quiz = Quiz.toQuizEntity(dto);

        return quizRepository.save(quiz);
    }

    @Override
    @Transactional
    public Quiz updateQuiz(Long id, QuizRequestDto dto) {

        if(dto.getAnswer().isEmpty() || dto.getContent().isEmpty()){
            throw new IllegalArgumentException("Answer and Content are required");
        }

       Quiz quiz = quizRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Quiz not found"));
        quiz.toUpdateQuizEntity(dto);

        return quiz;
    }

    @Override
    public Quiz findById(Long id) {

        Optional<Quiz> quiz = quizRepository.findById(id);

        if(quiz.isEmpty()){
            throw new IllegalArgumentException(String.format("Quiz with id %s not found", id));
        }

        return quiz.get();
    }

    @Override
    public Quiz findByContent(String content) {

        Optional<Quiz> quiz = quizRepository.findByContent(content);

        if(quiz.isEmpty()){
            throw new IllegalArgumentException(String.format("Quiz with content %s not found", content));
        }

        return quiz.get();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {

        Optional<Quiz> quiz = quizRepository.findById(id);

        if(quiz.isEmpty()){
            throw new IllegalArgumentException(String.format("Quiz with id %s not found", id));
        }

        quizRepository.deleteById(id);
    }

    @Override
    public List<QuizResponseDto> retrieveAllQuizs() {

        List<Quiz> quizzes = quizRepository.findAll();

        // quzzes -> quizResponseDto
        List<QuizResponseDto> collect = quizzes.stream()
                .map(quiz -> QuizResponseDto.builder()
                        .id(quiz.getId())
                        .content(quiz.getContent())
                        .answer(quiz.getAnswer())
                        .build())
                .collect(Collectors.toList());

        return collect;

    }

    @Override
    @Transactional
    public int updateCreateDateFromNow(LocalDateTime now) {

        return quizRepository.bulkCreateDatePlus(now);
    }
}
