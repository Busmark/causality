package oscar.gmail.com.causality.question;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "question_table")
public class Question {

//    @PrimaryKey()
//    @NonNull
//    private int id;


    private boolean isActive = true;

    @NonNull
    private String questionType;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "text")
    private String text;




    public Question(boolean isActive, @NonNull String questionType, @NonNull String text) {
        this.isActive = isActive;
        this.questionType = questionType;
        this.text = text;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @NonNull
    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(@NonNull String questionType) {
        this.questionType = questionType;
    }

    @NonNull
    public String getText() {
        return text;
    }

    public void setText(@NonNull String text) {
        this.text = text;
    }
}
