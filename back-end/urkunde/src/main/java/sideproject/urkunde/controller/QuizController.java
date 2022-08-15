package sideproject.urkunde.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "Quiz Controller : 퀴즈 CRUD")
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
    @ApiOperation(value="모든 퀴즈 조회" , notes = "해당 api로 요청시 모든 퀴즈 데이터를 가져온다.")
    public ResponseEntity<List<QuizResponseDto>> retrieveAllQuizs() {
        List<QuizResponseDto> quizzes = quizService.retrieveAllQuizs();
        return ResponseEntity.ok(quizzes);
    }

    /**
     * 쿼즈 조회
     * @param id 쿼즈 고유 번호
     * @return 조회된 퀴즈
     */
    @GetMapping(value = "/quizs/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="id로 조회하는 퀴즈" , notes = "해당 api로 요청시 id에 해당하는 퀴즈 데이터를 가져온다.")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id" , value = "퀴즈 고유 아이디" , required = true , paramType = "path")
            //, @ApiImplicitParam(name = "y", value = "y 값", required = true, dataType = "int", paramType = "query")
    })
    public ResponseEntity<QuizResponseDto> searchQuizWithId(@PathVariable Long id) {
        Quiz quiz = quizService.findById(id);
        QuizResponseDto quizResponseDto = QuizResponseDto.toDto(quiz);
        return ResponseEntity.ok(quizResponseDto);
    }

    /**
     * 쿼즈 생성
     * @param dto 퀴즈 생성에 필요한 필드 데이터
     * @return 퀴즈가 생성되었다는 응답
     */
    @PostMapping(value = "/quizs" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="퀴즈 생성하는 api" , notes = "해당 api로 요청시 저장하고자 하는 퀴즈 데이터가 필요합니다.")
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
    @ApiOperation(value="퀴즈 삭제하는 api" , notes = "해당 api로 요청시 id에 해당하는 퀴즈를 삭제합니다.")
    @ApiImplicitParam(name = "id" , value = "퀴즈 고유 아이디" , required = true , paramType = "path")
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
    @ApiOperation(value="퀴즈 수정하는 api" , notes = "해당 api로 요청시 id에 해당하는 퀴즈를 수정합니다. / 해당 수정폼에 있는 데이터 그대로 수정 반영됩니다.")
    @ApiImplicitParam(name = "id" , value = "퀴즈 고유 아이디" , required = true , paramType = "path")
    public ResponseEntity<QuizResponseDto> updateQuiz(@PathVariable Long id, @RequestBody QuizRequestDto dto) {
        Quiz quiz = quizService.updateQuiz(id, dto);
        QuizResponseDto quizResponseDto = QuizResponseDto.toDto(quiz);
        return ResponseEntity.ok(quizResponseDto);
    }


    /**
     * 퀴즈 전체 삭제
     */
    @DeleteMapping(value = "/quizs" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="퀴즈 전체 삭제하는 api" , notes = "해당 api로 요청시 퀴즈 전체를 삭제합니다.")
    public ResponseEntity<GeneralResponse> deleteAllQuiz() {
        quizService.deleteAll();

        return new ResponseEntity<>(GeneralResponse.of(200L, HttpStatus.OK , "퀴즈 전체를 성공적으로 삭제했습니다."), HttpStatus.OK);
    }



}
