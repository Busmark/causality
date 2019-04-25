package oscar.gmail.com.causality.effect;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface EffectDao {

    @Query("SELECT * from effect_table ORDER BY text ASC")
    LiveData<List<Effect>> getAlphabetizedEffects();

    @Insert
    void insert(Effect effect);

    @Query("DELETE FROM effect_table")
    void deleteAll();
}
