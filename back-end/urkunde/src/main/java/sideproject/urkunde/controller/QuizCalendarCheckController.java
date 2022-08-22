package sideproject.urkunde.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import sideproject.urkunde.dto.request.QuizCalendarCheck;
import sideproject.urkunde.dto.response.WeekCheckResponse;
import sideproject.urkunde.service.QuizCalendarCheckService;

import javax.validation.Valid;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@RestController
@RequiredArgsConstructor
@Api(tags = "Quiz Calendar Check Controller : 퀴즈 주간 단위로 check")
public class QuizCalendarCheckController {

    private final QuizCalendarCheckService quizCalendarCheckService;

    @PostMapping(value = "/week/check" , produces = APPLICATION_JSON_VALUE)
    @ApiOperation(value="퀴즈 주간 확인" , notes = "일요일부터 현재 날짜까지 퀴즈 맞춘 상태를 반환한다. ture: 다 풀었을 경우, false : 덜 풀었을 경우" +
            " none : 퀴즈 자체를 풀지 않는 경우")
    @ApiResponse(code = 200, message = "퀴즈 주간 확인 성공" ,
            examples = @Example(value = @ExampleProperty(mediaType = APPLICATION_JSON_VALUE,
                    value = "{'sunday' : 'ture' , 'monday' : 'false' , 'tuesday' : 'none'}")))
    public ResponseEntity<WeekCheckResponse> checkWeek(@RequestBody @Valid QuizCalendarCheck quizCalendarCheck) {

       // WeekCheckResponse weekChecks = quizCalendarCheckService.findCycleIdBetweenDates(quizCalendarCheck);

        WeekCheckResponse weekChecks = quizCalendarCheckService.findStatusBetweenDates(quizCalendarCheck);

        return ResponseEntity.ok(weekChecks);
    }


}
