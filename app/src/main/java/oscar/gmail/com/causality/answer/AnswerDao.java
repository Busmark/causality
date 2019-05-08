package oscar.gmail.com.causality.answer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


@Dao
public interface AnswerDao {

    @Query("SELECT * from answer_table")
    LiveData<List<Answer>> getAllAnswers();

//    @Query("SELECT * from answer_table WHERE fk_question_id LIKE :question_id ")
//    LiveData<List<Answer>> getAllAnswerToAQuestion(String question_id);

    @Insert
    void insert(Answer answer);

    @Query("DELETE FROM answer_table")
    void deleteAll();
}

