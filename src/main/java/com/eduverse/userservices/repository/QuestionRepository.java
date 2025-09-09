package com.eduverse.userservices.repository;

import com.eduverse.userservices.model.Question;
import com.eduverse.userservices.model.enums.DifficultyLevel;
import com.eduverse.userservices.model.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, String> {
    
    @Query("SELECT q FROM Question q WHERE q.quiz_id = :quizId")
    List<Question> findByQuizId(@Param("quizId") String quizId);
    
    List<Question> findByDifficulty(DifficultyLevel difficulty);
    
    List<Question> findByType(QuestionType type);
    
    @Query("SELECT q FROM Question q WHERE q.quiz_id = :quizId AND q.difficulty = :difficulty")
    List<Question> findByQuizIdAndDifficulty(@Param("quizId") String quizId, @Param("difficulty") DifficultyLevel difficulty);
    
    @Query("SELECT q FROM Question q WHERE q.quiz_id = :quizId AND q.type = :type")
    List<Question> findByQuizIdAndType(@Param("quizId") String quizId, @Param("type") QuestionType type);
    
    @Query("SELECT COUNT(q) FROM Question q WHERE q.quiz_id = :quizId")
    Long countByQuiz_id(@Param("quizId") String quizId);
}
