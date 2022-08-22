package sideproject.urkunde.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sideproject.urkunde.repository.DayCheckRepository;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DayCheckTest {

    @Autowired
    DayCheckRepository dayCheckRepository;

    @Test
    public void saveTest(){

        DayCheck day = new DayCheck("none");

        dayCheckRepository.save(day);

        System.out.println(day.getDate());


    }

}