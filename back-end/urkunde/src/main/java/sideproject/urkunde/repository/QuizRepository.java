package sideproject.urkunde.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sideproject.urkunde.domain.Quiz;

import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    //find by content
    @Query("SELECT q FROM Quiz q WHERE q.content = :content")
    Optional<Quiz> findByContent(@Param("content") String content);

}
