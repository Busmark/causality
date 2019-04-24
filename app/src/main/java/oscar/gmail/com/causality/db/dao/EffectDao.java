package oscar.gmail.com.causality.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import oscar.gmail.com.causality.db.entity.EffectEntity;

@Dao
public interface EffectDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    void insert(EffectEntity effectEntity);

}
