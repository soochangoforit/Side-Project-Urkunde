package sideproject.urkunde.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sideproject.urkunde.domain.DayCheck;
import sideproject.urkunde.domain.Quiz;
import sideproject.urkunde.domain.QuizCheck;
import sideproject.urkunde.domain.QuizCycle;
import sideproject.urkunde.repository.DayCheckRepository;
import sideproject.urkunde.repository.QuizCheckRepository;
import sideproject.urkunde.repository.QuizCycleRepository;
import sideproject.urkunde.repository.QuizRepository;
import sideproject.urkunde.service.DayCheckService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DayCheckServiceImpl implements DayCheckService {

    private final QuizCheckRepository quizCheckRepository;
    private final QuizRepository quizRepository;
    private final DayCheckRepository dayCheckRepository;
    private final QuizCycleRepository quizCycleRepository;

    @Override
    @Transactional
    public void updateDayCheck(LocalDate localDate) {

        LocalDateTime startTime = startLocalDateToLocalDateTime(localDate); // day 00:00:00
        LocalDateTime endTime = endLocalDateToLocalDateTime(localDate); // day 23:59:59

        // 해당 날짜에 온전히 끝낸 사이클에 대해서만 체크한다. 반드시 점검 끝내기 버튼 눌렀을때만 적용
        // 해당 요일에 시도했던 모든 사이클을 가져온다.
        List<QuizCycle> quizCycles = quizCycleRepository.findEndTimeBetween(startTime, endTime);


        // 하나의 요일에 시도햇던 모든 사이클 아이디를 가져온다.
        List<Long> quizCycleIds = quizCycles.stream().map(QuizCycle::getId).collect(Collectors.toList());


        // 해당 요일의 모든 사이클을 통해서 check된 모든 quiz check를 가져온다.
        List<QuizCheck> quizChecksForSuccess = quizCheckRepository.findCorrectByQuizCycleIdIn(quizCycleIds); // todo : quizCheck와 quiz Join을 통해서 가져오는데 인자로은 cycle id를 가져오도록 하자.
        List<QuizCheck> quizCheckForFails = quizCheckRepository.findInCorrectByQuizCycleIdIn(quizCycleIds);


        int correctSize = (int) quizChecksForSuccess.stream().map(QuizCheck::getQuiz).mapToLong(Quiz::getId).distinct().count();
        int incorrectSize = (int) quizCheckForFails.stream().map(QuizCheck::getQuiz).mapToLong(Quiz::getId).distinct().count();

        int allQuizOfSize = quizRepository.findAll().size();// 전체 퀴즈의 개수

        String dayState = "none";
        if (correctSize == allQuizOfSize) {
            dayState = "true";
        } else if (correctSize != 0 && correctSize < allQuizOfSize) {
            dayState = "false";
        } else if (incorrectSize != 0) {
            dayState = "false";
        }



        //update 혹은 save 분기문 필요 , 해당 날짜에 이미 상태값이 있는지 확인 필요
        Optional<DayCheck> dayCheckIsExist = dayCheckRepository.findById(LocalDate.now());

        // 이미 존재하는 경우에 대해서는 상태값 업데이트 dirty checking 이 필요하다.
            if(dayCheckIsExist.isPresent())

        {
            dayCheckIsExist.get().updateDayState(dayState);
        }else

        {
            DayCheck dayCheck = DayCheck.builder()
                    .dayState(dayState)
                    .build();
            dayCheckRepository.save(dayCheck);
        }

    }






//
//
//
//
//
//
//
//
//        List<QuizCheck> correctQuizCheckOfDay = quizCheckRepository.findByQuizCycleIdWithCorrect(quizCycleId);
//
//        // 여기서 과연 중복을 제거할 이유가 있을까? 없다!!!
//
//        // get size of correctQuizCheckOfDay
//        int correctSize = correctQuizCheckOfDay.size(); // 해당 사이클에서 문제를 맞춘 개수
//
//        // get size of all quizs
//        int allQuizOfSize = quizRepository.findAll().size();// 전체 퀴즈의 개수
//
//        String dayState = "none";
//        if(correctSize != 0 && correctSize == allQuizOfSize) {
//            dayState = "true";
//        }else if(correctSize != 0 && correctSize < allQuizOfSize) {
//            dayState = "false";
//        }
//
//        //update 혹은 save 분기문 필요 , 해당 날짜에 이미 상태값이 있는지 확인 필요
//        Optional<DayCheck> dayCheckIsExist = dayCheckRepository.findById(LocalDate.now());
//
//        // 이미 존재하는 경우에 대해서는 상태값 업데이트 dirty checking 이 필요하다.
//        if(dayCheckIsExist.isPresent()){
//            dayCheckIsExist.get().updateDayState(dayState);
//        }else{
//            DayCheck dayCheck = DayCheck.builder()
//                    .dayState(dayState)
//                    .build();
//            dayCheckRepository.save(dayCheck);
        // end of if





    public LocalDateTime startLocalDateToLocalDateTime(LocalDate date){
        LocalDateTime startTime = date.atTime(0, 0, 0);
        return startTime;
    }

    public LocalDateTime endLocalDateToLocalDateTime(LocalDate date){
        LocalDateTime endTime = date.atTime(23, 59, 59);
        return endTime;
    }





    @Override
    public Optional<DayCheck> findStatusOfDay(LocalDate localDate) {
        Optional<DayCheck> dayCheck = dayCheckRepository.findById(localDate);

        return dayCheck;
    }


}
