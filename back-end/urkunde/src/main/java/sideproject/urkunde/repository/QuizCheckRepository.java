package sideproject.urkunde.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sideproject.urkunde.domain.QuizCheck;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuizCheckRepository extends JpaRepository<QuizCheck, Long> {

    @Query(value = "select qc from QuizCheck qc where qc.quiz.id= :quizId and qc.quizCycle.id = :quizCycleId")
    Optional<QuizCheck> findByQuizAndQuizCycle(@Param("quizId") Long quizId,@Param("quizCycleId") Long quizCycle);

    // fetch join with quiz
    @Query("select qc from QuizCheck qc join fetch qc.quiz  where qc.quizCycle.id in :quizCycleIds")
    List<QuizCheck> findByQuizCycleIdIn(@Param("quizCycleIds") List<Long> quizCycleIds);
}
