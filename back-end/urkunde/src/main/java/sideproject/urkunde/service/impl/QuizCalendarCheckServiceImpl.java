package sideproject.urkunde.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.urkunde.domain.DayCheck;
import sideproject.urkunde.domain.Quiz;
import sideproject.urkunde.domain.QuizCheck;
import sideproject.urkunde.domain.QuizCycle;
import sideproject.urkunde.dto.request.QuizCalendarCheck;
import sideproject.urkunde.dto.response.WeekCheckResponse;
import sideproject.urkunde.repository.QuizCheckRepository;
import sideproject.urkunde.repository.QuizCycleRepository;
import sideproject.urkunde.repository.QuizRepository;
import sideproject.urkunde.service.DayCheckService;
import sideproject.urkunde.service.QuizCalendarCheckService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class QuizCalendarCheckServiceImpl implements QuizCalendarCheckService {

    private final QuizCycleRepository quizCycleRepository;
    private final QuizRepository quizRepository;
    private final QuizCheckRepository quizCheckRepository;
    private final DayCheckService dayCheckService;


    @Override
    public WeekCheckResponse findCycleIdBetweenDates(QuizCalendarCheck quizCalendarCheck) {


        Map<DayOfWeek, String> datesForCheck = new LinkedHashMap<>();

        // 일 ~ 수요일까지 진행했던 모든 사이클이 나온다.  다만 반드시 사이클을 종료한것에 대해서만 인정한다.....
        List<LocalDate> localDates = quizCalendarCheck.datesBetweenStartDateToRecentDate();

        // 요일별로 for문을 돈다.
        for (LocalDate localDate : localDates) {
            LocalDateTime startTime = startLocalDateToLocalDateTime(localDate); // day 00:00:00
            LocalDateTime endTime = endLocalDateToLocalDateTime(localDate); // day 23:59:59

            // 해당 날짜에 온전히 끝낸 사이클에 대해서만 체크한다. 반드시 점검 끝내기 버튼 눌렀을때만 적용
            // 해당 요일에 시도했던 모든 사이클을 가져온다.
            List<QuizCycle> quizCycles = quizCycleRepository.findEndTimeBetween(startTime, endTime);

            // todo : 해당 요일에 시도했던 퀴즈 사이클이 없는 경우 , null로 들어오고, 이 경우 Map에 "none"이라고 넣는다.
            // todo : size 0 or null please check
            if (quizCycles.size() == 0) {
                datesForCheck.put(localDate.getDayOfWeek(), "none");
                continue;
            }


            // 하나의 요일에 시도햇던 모든 사이클 아이디를 가져온다.
            List<Long> quizCycleIds = quizCycles.stream().map(QuizCycle::getId).collect(Collectors.toList());

            // 해당 요일의 모든 사이클에 의해 시도되었던 check 중에서 맞는 quiz Id가 담겨 있다.
            LinkedList<Long> correctAboutQuizIds = new LinkedList<>();

            // 해당 요일의 모든 사이클을 통해서 check된 모든 quiz check를 가져온다.
            List<QuizCheck> quizIdsForSuccess = quizCheckRepository.findCorrectByQuizCycleIdIn(quizCycleIds); // todo : quizCheck와 quiz Join을 통해서 가져오는데 인자로은 cycle id를 가져오도록 하자.

            int quizIdsForSuccessCount = quizIdsForSuccess.stream().map(QuizCheck::getQuiz).map(Quiz::getId).collect(Collectors.toSet()).size();

            // 그날에 만들었던 모든 문제 아이디 개수를 가져온다.
            int idsMadeOfDay = quizRepository.findByCreatedDateBetween(startTime, endTime).size();

            // 그날에 시도한 사이클 흔적은 있지만 문제를 다 제거해서 없어진 경우는 none
            if (quizIdsForSuccessCount == idsMadeOfDay && quizIdsForSuccessCount != 0) {
                datesForCheck.put(localDate.getDayOfWeek(), "true");
            } else if (quizIdsForSuccessCount < idsMadeOfDay && quizIdsForSuccessCount != 0) { // 해당 요일 기준으로 만든 문제의 개수를 다 풀지 않을 경우 (모두 정답이 아닌 경우)
                datesForCheck.put(localDate.getDayOfWeek(), "false");
            } else { // none을 또 넣어주는 이유는 해당 날짜에 시도한 흔적은 있으나, 문제를 다 제거해서 없어진 경우
                datesForCheck.put(localDate.getDayOfWeek(), "none");
            }


            // 만약 한참전에 문제를 만들고 오늘 다시 문제를 추가해서 만드는 경우 , 내가 만든 로직은 오늘 만든 문제 즉, 1개에 대해서만
            // 맞았는지 아닌지 확인한다.
            // todo : 그걸 방지하기 위해서는 Quiz Table을 날짜가 지나갈때마다 createDate 1일씩 업데이트 되어야 한다.
            // todo : 만약 사이클이 시작할때마다 시간이 현재 날짜 기준으로 update 필요
            // todo : 풀지 않는 날짜에 대해서는 매일 업데이트 될 필요성은 없다.


        } // end of for loop


        return new WeekCheckResponse(datesForCheck);
    }

    @Override
    public WeekCheckResponse findStatusBetweenDates(QuizCalendarCheck quizCalendarCheck) {

        Map<DayOfWeek, String> datesForCheck = new LinkedHashMap<>();

        List<LocalDate> localDates = quizCalendarCheck.datesBetweenStartDateToRecentDate();

        for (LocalDate localDate : localDates) {

            Optional<DayCheck> statusOfDay = dayCheckService.findStatusOfDay(localDate);

            // 해당 날짜 안에 문제를 다 틀리더라도 사이클을 돌았으면 흔적이 남아있다.
            if (statusOfDay.isPresent()) {
                datesForCheck.put(localDate.getDayOfWeek(), statusOfDay.get().getDayState());
            }
            // 해당 요일에 사이클 조차 시작하지 않은 경우는 none이다.
            else {
                datesForCheck.put(localDate.getDayOfWeek(), "none");
            }
        }

        return new WeekCheckResponse(datesForCheck);

    }








    public LocalDateTime startLocalDateToLocalDateTime(LocalDate date){
        ;
        LocalDateTime startTime = date.atTime(0, 0, 0);
        return startTime;
    }

    public LocalDateTime endLocalDateToLocalDateTime(LocalDate date){
        ;
        LocalDateTime endTime = date.atTime(23, 59, 59);
        return endTime;
    }


}
