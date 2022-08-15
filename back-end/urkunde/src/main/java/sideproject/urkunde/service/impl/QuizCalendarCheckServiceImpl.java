package sideproject.urkunde.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import sideproject.urkunde.domain.QuizCheck;
import sideproject.urkunde.domain.QuizCycle;
import sideproject.urkunde.dto.request.QuizCalendarCheck;
import sideproject.urkunde.repository.QuizCheckRepository;
import sideproject.urkunde.repository.QuizCycleRepository;
import sideproject.urkunde.repository.QuizRepository;
import sideproject.urkunde.service.QuizCalendarCheckService;

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



    @Override
    public Map<LocalDate, String> findCycleIdBetweenDates(QuizCalendarCheck quizCalendarCheck) {


        Map<LocalDate, String> datesForCheck = new LinkedHashMap<>();

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
                datesForCheck.put(localDate, "none");
                continue;
            }


            // 하나의 요일에 시도햇던 모든 사이클 아이디를 가져온다.
            List<Long> quizCycleIds = quizCycles.stream().map(QuizCycle::getId).collect(Collectors.toList());

            // 해당 요일의 모든 사이클에 의해 시도되었던 check 중에서 맞는 quiz Id가 담겨 있다.
            LinkedList<Long> correctAboutQuizIds = new LinkedList<>();

            // 해당 요일의 모든 사이클을 통해서 check된 모든 quiz check를 가져온다.
            List<QuizCheck> quizChecks = quizCheckRepository.findByQuizCycleIdIn(quizCycleIds); // todo : quizCheck와 quiz Join을 통해서 가져오는데 인자로은 cycle id를 가져오도록 하자.

            for (QuizCheck quizCheck : quizChecks) {
                if (quizCheck.getQuizState().getState().equals("correct")) {
                    correctAboutQuizIds.add(quizCheck.getQuiz().getId()); // todo : lazy loading이 일어나는지 아닌지 확인해야 한다. 확인
                }
            }

            Set<Long> setOfIds = new LinkedHashSet<>(correctAboutQuizIds);// 맞춘 문제 아이디에 대해서 중복제거

            // 그날에 만들었던 모든 문제 아이디 개수를 가져온다.
            List<Long> idsMadeOfDay = quizRepository.findByCreatedDateBetween(startTime, endTime);

            // 해당 요일에 만든 문제의 개수랑 해당 요일에 맞췄던 문제의 개수를 비교한다.
            int size = idsMadeOfDay.size();


            // 그날에 시도한 사이클 흔적은 있지만 문제를 다 제거해서 없어진 경우는 none
            if (setOfIds.size() == size && setOfIds.size() != 0) {
                datesForCheck.put(localDate, "true");
            } else if(setOfIds.size() < size && setOfIds.size() != 0){ // 해당 요일 기준으로 만든 문제의 개수를 다 풀지 않을 경우 (모두 정답이 아닌 경우)
                datesForCheck.put(localDate, "false");
            }else{
                datesForCheck.put(localDate, "none");
            }


            // 만약 한참전에 문제를 만들고 오늘 다시 문제를 추가해서 만드는 경우 , 내가 만든 로직은 오늘 만든 문제 즉, 1개에 대해서만
            // 맞았는지 아닌지 확인한다.
            // todo : 그걸 방지하기 위해서는 Quiz Table을 날짜가 지나갈때마다 createDate 1일씩 업데이트 되어야 한다.
            // todo : 만약 사이클이 시작할때마다 시간이 현재 날짜 기준으로 update 필요
            // todo : 풀지 않는 날짜에 대해서는 매일 업데이트 될 필요성은 없다.


        } // end of for loop


        return datesForCheck;
    }



//
////        LocalDateTime startDate = quizCalendarCheck.getStartDate();
////        LocalDateTime recentDate = quizCalendarCheck.getRecentDate();
//
//
//
//
//
//
//        List<QuizCycle> quizCycles = quizCycleRepository.findEndTimeBetween(quizCalendarCheck.getStartDate(), quizCalendarCheck.getRecentDate());
//
//        // group by QuizCycle endTime
//        // 사이클이 올바로 끝난 사이클에 중에서 요일별로 사이클 아이디를 묶어준다.
//        Map<String, List<QuizCycle>> collect = quizCycles.stream().collect(Collectors.groupingBy(QuizCycle::getOnlyYMD));
//
//        // 최고전역변수
//        // 하루에 하나의 사이클을 했으면 괜찮은데 그러지 못한 경우도 반드시 존재할거 같다.
//        // 그러면 순서대로 담을순 없을거 같다는 생각이 든다.
//        // Map형식으로 반환을 할까?
//
//
//        List<String> dayCheck = new ArrayList<>(); // { "true" , "false" , "false" , "false" , "false" , "none" , "none" }
//
//        // 날짜별로 순환
//        for(String key : collect.keySet()) {
//            List<QuizCycle> quizCycles1 = collect.get(key); // 해당 요일에 완료한 사이클 목록
//
//            LinkedList<Long> correctAboutQuizIds = new LinkedList<>(); // 해당 요일 반복문에 대해서는 지역 변수 지정, 추후 set을 통해서 중복 제거
//
//            // 하나의 요일에 해당하는 QuizCycle 리스트의 모든 id 값을 가져온다.
//            List<Long> quizCycleIds = quizCycles1.stream().map(QuizCycle::getId).collect(Collectors.toList());
//
//            // 요일에 해당하는 모든 사이클 아이디에 대한  Quiz Check id 리스트를 가져온다. -> 그날 시도한 모든 문제 체크 아이디 목록
//            List<QuizCheck> quizChecks = quizCheckRepository.findByQuizCycleIdIn(quizCycleIds);
//
//            // 가져온 모든 quizChecks를 순회하면서 state가 correct인 id을 correctAboutQuizIds에 담는다.
//            for (QuizCheck quizCheck : quizChecks) {
//                if (quizCheck.getQuizState().getState().equals("correct")) {
//                    correctAboutQuizIds.add(quizCheck.getQuiz().getId());
//                }
//            }
//
//            // 이제 correctAboutQuizIds에는 정답이 맞은 퀴즈의 id가 담겨있다.
//            // set을 이용하여 중복을 제거한다.
//            Set<Long> set = new HashSet<>(correctAboutQuizIds);
//
//            // 중복을 제거한 맞춘 문제의 아이디 개수와 , 현 요일 시점에서 사이클 id 기준으로 정답이 맞은 문제의 아이디 개수를 비교한다.
//            // 여기서 중요한점은 해당 요일에 만든 개수와 비교를 해서 개수가 같다면 ture or false를 반환한다.
//            // 반드시 해당 요일을 기준으로 해야한다.
//
//            // 위에서 선언한 key가 바로 해당 요일이다.
//            // key to localDateTime
//            LocalDate day = LocalDate.parse(key, DateTimeFormatter.ISO_DATE);
//            LocalDateTime startTime = day.atTime(0, 0, 0);
//            LocalDateTime endTime = day.atTime(23, 59, 59);
//
//            // 그날에 만들었던 모든 문제 아이디 개수를 가져온다.
//            List<Long> idsMadeOfDay = quizRepository.findByCreatedDateBetween(startTime,endTime);
//
//            int size = idsMadeOfDay.size();
//            if (set.size() == size) {
//                dayCheck.add("true");
//            } else if( set.size() < size) {
//                dayCheck.add("false");
//            }else{
//
//            }
//
//        }// 해당 요일에 대한 true , false 반환
//
//
//        return null;
//    }




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
