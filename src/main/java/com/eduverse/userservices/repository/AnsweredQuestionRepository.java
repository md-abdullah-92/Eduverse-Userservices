package com.eduverse.userservices.repository;

import com.eduverse.userservices.model.AnsweredQuestion;
import com.eduverse.userservices.model.enums.DifficultyLevel;
import com.eduverse.userservices.model.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnsweredQuestionRepository extends JpaRepository<AnsweredQuestion, String> {
    
    @Query("SELECT aq FROM AnsweredQuestion aq WHERE aq.quiz_result_id = :quizResultId")
    List<AnsweredQuestion> findByQuizResultId(@Param("quizResultId") String quizResultId);
    
    @Query("SELECT aq FROM AnsweredQuestion aq WHERE aq.quiz_id = :quizId")
    List<AnsweredQuestion> findByQuizId(@Param("quizId") String quizId);
    
    List<AnsweredQuestion> findByDifficulty(DifficultyLevel difficulty);
    
    List<AnsweredQuestion> findByType(QuestionType type);
    
    @Query("SELECT aq FROM AnsweredQuestion aq WHERE aq.quiz_result_id = :quizResultId AND aq.correct_answer = aq.user_answer")
    List<AnsweredQuestion> findCorrectAnswersByQuiz_result_id(@Param("quizResultId") String quizResultId);
    
    @Query("SELECT aq FROM AnsweredQuestion aq WHERE aq.quiz_result_id = :quizResultId AND aq.correct_answer != aq.user_answer")
    List<AnsweredQuestion> findIncorrectAnswersByQuiz_result_id(@Param("quizResultId") String quizResultId);
    
    @Query("SELECT COUNT(aq) FROM AnsweredQuestion aq WHERE aq.quiz_result_id = :quizResultId AND aq.correct_answer = aq.user_answer")
    Long countCorrectAnswersByQuiz_result_id(@Param("quizResultId") String quizResultId);
}
