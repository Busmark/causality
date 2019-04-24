package oscar.gmail.com.causality.answer;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface AnswerDao {

    // We do not need a conflict strategy, because the answerText is our primary key, and you cannot
    // add two items with the same primary key to the database. If the table has more than one
    // column, you can use @Insert(onConflict = OnConflictStrategy.REPLACE) to update a row.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Answer answer);

    @Query("Select * from answer")
    List<Answer> loadAll();

    @Query("Select * from answer where answer_id IN (:question)")
    List<Answer> loadAllByQuestion(int... question);

    @Delete
    void delete(Answer answer);
}
