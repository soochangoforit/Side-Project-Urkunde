package sideproject.urkunde.repository;

import net.bytebuddy.asm.Advice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sideproject.urkunde.domain.Quiz;
import sideproject.urkunde.domain.QuizCheck;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    //find by content
    @Query("SELECT q FROM Quiz q WHERE q.content = :content")
    Optional<Quiz> findByContent(@Param("content") String content);

    @Query("SELECT q.id FROM Quiz q WHERE q.createdDate BETWEEN :startTime AND :endTime")
    List<Long> findByCreatedDateBetween(@Param("startTime") LocalDateTime startTime , @Param("endTime") LocalDateTime endTime);

    // update quiz createdDate to current time
    @Modifying(clearAutomatically = true)
    @Query("UPDATE Quiz q SET q.createdDate = :now")
    int bulkCreateDatePlus(@Param("now") LocalDateTime now);
}
