package sideproject.urkunde.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.urkunde.domain.QuizCycle;

@Repository
public interface QuizCycleRepository extends JpaRepository<QuizCycle, Long> {

}
