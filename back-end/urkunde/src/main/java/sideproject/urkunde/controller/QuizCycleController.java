package sideproject.urkunde.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sideproject.urkunde.dto.request.QuizCycleEndRequest;
import sideproject.urkunde.dto.response.QuizCycleResultResponse;
import sideproject.urkunde.dto.response.QuizCycleStartResponse;
import sideproject.urkunde.repository.QuizRepository;
import sideproject.urkunde.service.DayCheckService;
import sideproject.urkunde.service.QuizCycleService;
import sideproject.urkunde.service.QuizService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Api(tags = "Quiz Cycle Controller : 퀴즈 사이클의 시작과 종료")
public class QuizCycleController {

    private final QuizCycleService  quizCycleService;

    private final QuizService quizService;

    private final DayCheckService dayCheckService;

    /**
     * 17일 수정
     * 사이클을 끝낼때마다 그날에 대한 LocalDate를 만들어서 상태값을 계속해서 update할 예정이다.
     * 따라서 굳이 문제의 createDate를 업데이트하지 않아도 된다.
     */
    @GetMapping(value="/quiz/cycle/start" , produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="퀴즈 사이클의 시작" , notes = "해당 api로 요청시 퀴즈 사이클을 시작한다.")
    public ResponseEntity<QuizCycleStartResponse> startQuizCycle() {


//        LocalDateTime now = LocalDateTime.now();
//        // update createdDate for bulk insert
//        quizService.updateCreateDateFromNow(now);

        // 퀴즈 사이클 시작
        QuizCycleStartResponse quizCycleStartResponse = quizCycleService.startQuizCycle();

        return ResponseEntity.ok(quizCycleStartResponse);
    }

    /**
     * 17일 추가 작업, 사이클이 종료될때 해당 localDate를 이용해서 DayCheck의 상태를 persist 해준다.
     */
    @PostMapping(value="/quiz/cycle/end", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value="퀴즈 사이클의 종료" , notes = "해당 api로 요청시 퀴즈 사이클을 종료한다. 맞춘개수를 반환한다.")
    public ResponseEntity<QuizCycleResultResponse> endQuizCycle(@RequestBody @Valid QuizCycleEndRequest quizCycleEndRequest) {

        // 퀴즈 사이클 종료
        QuizCycleResultResponse quizCycleResultResponse = quizCycleService.endQuizCycle(quizCycleEndRequest.getCycleId());

        // 종료한 사이클 아이디를 통해서 각 문제를 순회하고

        // 사용자가 만든 문제의 개수와 맞춘 개수를 비교하면 true ,false를 생각하여

        // day check repository를 이용해서 persist 해준다.
        dayCheckService.updateDayCheck(LocalDate.now());


        return ResponseEntity.ok(quizCycleResultResponse);
    }

}
