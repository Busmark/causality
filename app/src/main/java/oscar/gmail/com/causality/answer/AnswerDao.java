package oscar.gmail.com.causality.answer;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

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

