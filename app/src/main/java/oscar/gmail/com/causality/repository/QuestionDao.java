package oscar.gmail.com.causality.repository;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
