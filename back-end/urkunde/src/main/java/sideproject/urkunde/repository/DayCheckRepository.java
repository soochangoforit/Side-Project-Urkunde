package sideproject.urkunde.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.urkunde.domain.DayCheck;

import java.time.LocalDate;

@Repository
public interface DayCheckRepository extends JpaRepository<DayCheck, LocalDate> {

}
