package oscar.gmail.com.causality.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import oscar.gmail.com.causality.model.Effect;

@Entity
public class EffectEntity implements Effect {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String text;

    public EffectEntity() {
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getText() {
        return null;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }
}
