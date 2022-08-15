package sideproject.urkunde.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sideproject.urkunde.domain.QuizCheck;
import sideproject.urkunde.domain.QuizCycle;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface QuizCycleRepository extends JpaRepository<QuizCycle, Long> {

    @Query("select qc from QuizCycle qc where qc.endTime between :startTime and :endTime")
    List<QuizCycle> findEndTimeBetween(@Param("startTime") LocalDateTime startDate, @Param("endTime") LocalDateTime endDate);

}
