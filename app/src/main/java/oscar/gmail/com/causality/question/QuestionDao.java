package oscar.gmail.com.causality.question;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface QuestionDao {

    @Query("SELECT * from question_table ORDER BY text ASC")
    LiveData<List<Question>> getAlphabetizedQuestions();

    @Insert
    void insert(Question question);

    @Query("DELETE FROM question_table")
    void deleteAll();
}
