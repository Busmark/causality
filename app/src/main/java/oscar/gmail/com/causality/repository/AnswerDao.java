package oscar.gmail.com.causality.repository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

/**
 *
 */
@Dao
public interface AnswerDao {

    @Query("SELECT * from answer_table")
    LiveData<List<Answer>> getAllAnswers();

    @Query("SELECT * from answer_table WHERE fk_question_id LIKE :question_id ")
    List<Answer> getAllAnswersToAQuestion(String question_id);

    @Insert
    void insert(Answer answer);

    @Query("DELETE FROM answer_table")
    void deleteAll();
}

