package oscar.gmail.com.causality.effect;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "effect_table")
public class Effect {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "text")
    private String text;


    public Effect(@NonNull String text) {
        this.text = text;
    }

    @NonNull
    public String getText() {
        return text;
    }

}
