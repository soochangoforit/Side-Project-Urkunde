package sideproject.urkunde.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sideproject.urkunde.domain.QuizCheck;

@Repository
public interface QuizCheckRepository extends JpaRepository<QuizCheck, Long> {

}
