package oscar.gmail.com.causality;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "question_table")
public class Question {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "question")
    private String questionText;
/*
    //använd denna för att kunna ta emot svaret från en notification och tilldela svaret till den här frågan.
    private UUID questionId;

    private Notification notification;

    private String[] answers;

    //hur sker upprepning?
    private Calendar calendar;

    //Ska denna sättas från auktorisationen eller ska jag ha en model med User som har ett antal Questions?
    private UUID questionOwner;
*/

    public Question(@NonNull String questionText) {
        this.questionText = questionText;
    }

    @NonNull
    public String getQuestionText() {
        return questionText;
    }
}
