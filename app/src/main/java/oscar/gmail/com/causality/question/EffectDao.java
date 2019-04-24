package oscar.gmail.com.causality.question;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EffectDao {

    @Query("SELECT * from Effect ORDER BY question ASC")
    LiveData<List<Effect>> getAlphabetizedQuestions();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Effect effect);

    @Query("DELETE FROM Effect")
    void deleteAll();
}
