package oscar.gmail.com.causality;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface QuestionDao {

    @Query("SELECT * from question_table ORDER BY question ASC")
    LiveData<List<Question>> getAlphabetizedQuestions();

    // We do not need a conflict strategy, because the question is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Question question);

    @Query("DELETE FROM question_table")
    void deleteAll();
}
