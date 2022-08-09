package sideproject.urkunde.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sideproject.urkunde.dto.request.QuizCheckRequest;
import sideproject.urkunde.dto.response.QuizCheckResponse;
import sideproject.urkunde.service.QuizCheckService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Api(tags = "Quiz Check Controller : 퀴즈 정답 확인")
public class QuizCheckController {

    private final QuizCheckService quizCheckService;


    @PostMapping(value = "/quiz/check/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="퀴즈 정답 확인" , notes = "해당 api로 요청시 퀴즈 정답을 확인한다.")
    @ApiImplicitParam(name = "id" , value = "퀴즈 고유 아이디" , required = true)
    public ResponseEntity<QuizCheckResponse> checkQuiz(@PathVariable("id") Long quizId, @RequestBody @Valid QuizCheckRequest quizCheckRequest) {

        // 퀴즈 정답 확인
        QuizCheckResponse quizCheckResponse = quizCheckService.checkQuiz(quizId, quizCheckRequest);

        return ResponseEntity.ok(quizCheckResponse);
    }

}
