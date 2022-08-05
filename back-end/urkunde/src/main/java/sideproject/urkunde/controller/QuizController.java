package sideproject.urkunde.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sideproject.urkunde.domain.Quiz;
import sideproject.urkunde.dto.request.QuizRequestDto;
import sideproject.urkunde.dto.response.GeneralResponse;
import sideproject.urkunde.dto.response.QuizResponseDto;
import sideproject.urkunde.service.QuizService;

import java.util.List;

/**
 * 퀴즈 CRUD에 매핑되는 각각의 Controller
 */
@RestController
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /**
     * 퀴즈 목록 조회 -> 문제를 풀기 위해서
     * @return 퀴즈 목록
     */
    @GetMapping(value = "/quizs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<QuizResponseDto>> retrieveAllQuizs() {
        List<QuizResponseDto> quizzes = quizService.retrieveAllQuizs();

        return ResponseEntity.ok(quizzes);
    }

    /**
     * 쿼즈 조회
     * @param id 쿼즈 고유 번홓
     * @return 조회된 퀴즈
     */
    @GetMapping(value = "/quizs/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quiz> searchQuizWithId(@PathVariable Long id) {
        Quiz quiz = quizService.findById(id);

        return ResponseEntity.ok(quiz);
    }

    /**
     * 쿼즈 생성
     * @param dto 퀴즈 생성에 필요한 필드 데이터
     * @return 퀴즈가 생성되었다는 응답
     */
    @PostMapping(value = "/quizs" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse> createQuiz(@RequestBody QuizRequestDto dto) {
        Quiz quiz = quizService.createQuiz(dto);

        return new ResponseEntity<>(GeneralResponse.of(quiz.getId(), HttpStatus.CREATED, "퀴즈를 생성하였습니다."), HttpStatus.CREATED);
    }

    /**
     * 퀴즈 삭제
     * @param id 삭제한 퀴즈 아이디
     * @return 삭제되었다는 응답
     */
    @DeleteMapping(value = "/quizs/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GeneralResponse> deleteQuiz(@PathVariable Long id) {
        quizService.deleteById(id);

        return new ResponseEntity<>(GeneralResponse.of(id, HttpStatus.OK, "퀴즈를 삭제하였습니다."), HttpStatus.OK);
    }

    /**
     * 퀴즈 수정
     * @param id 수정할 퀴즈 아이디
     * @param dto 수정할 퀴즈 데이터
     * @return 수정된 퀴즈
     */
    @PutMapping(value = "/quizs/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Quiz> updateQuiz(@PathVariable Long id, @RequestBody QuizRequestDto dto) {
        Quiz quiz = quizService.updateQuiz(id, dto);
        return ResponseEntity.ok(quiz);
    }

}
