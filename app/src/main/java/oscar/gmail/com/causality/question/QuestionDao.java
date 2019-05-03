package oscar.gmail.com.causality.question;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

//todo: hantera cascade för om Question tas bort ska alla dess Answers ta bort också.
@Dao
public interface QuestionDao {

    @Query("SELECT * from question_table ORDER BY question_text ASC")
    LiveData<List<Question>> getAlphabetizedQuestions();

    @Insert
    void insert(Question question);

    @Query("DELETE FROM question_table")
    void deleteAll();

    @Query("DELETE FROM question_table")
    void deleteQuestion();

    @Query("SELECT question_id FROM question_table where question_text LIKE :question_text")
    String getQuestionId(String question_text);

}
