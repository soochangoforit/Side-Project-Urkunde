package sideproject.urkunde.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sideproject.urkunde.dto.request.QuizCycleEndRequest;
import sideproject.urkunde.dto.response.QuizCycleResultResponse;
import sideproject.urkunde.dto.response.QuizCycleStartResponse;
import sideproject.urkunde.repository.QuizRepository;
import sideproject.urkunde.service.QuizCycleService;
import sideproject.urkunde.service.QuizService;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Api(tags = "Quiz Cycle Controller : 퀴즈 사이클의 시작과 종료")
public class QuizCycleController {

    private final QuizCycleService  quizCycleService;

    private final QuizService quizService;

    @GetMapping(value="/quiz/cycle/start" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="퀴즈 사이클의 시작" , notes = "해당 api로 요청시 퀴즈 사이클을 시작한다.")
    public ResponseEntity<QuizCycleStartResponse> startQuizCycle() {

        LocalDateTime now = LocalDateTime.now();
        // update createdDate for bulk insert
        quizService.updateCreateDateFromNow(now);

        // 퀴즈 사이클 시작
        QuizCycleStartResponse quizCycleStartResponse = quizCycleService.startQuizCycle();

        return ResponseEntity.ok(quizCycleStartResponse);
    }

    @PostMapping(value="/quiz/cycle/end", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="퀴즈 사이클의 종료" , notes = "해당 api로 요청시 퀴즈 사이클을 종료한다. 맞춘개수를 반환한다.")
    public ResponseEntity<QuizCycleResultResponse> endQuizCycle(@RequestBody @Valid QuizCycleEndRequest quizCycleEndRequest) {

        // 퀴즈 사이클 종료
        QuizCycleResultResponse quizCycleResultResponse = quizCycleService.endQuizCycle(quizCycleEndRequest.getCycleId());

        return ResponseEntity.ok(quizCycleResultResponse);
    }

}
