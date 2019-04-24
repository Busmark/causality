package oscar.gmail.com.causality.question;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity(tableName = "question_table")
public class Question {

//    @PrimaryKey(autoGenerate = true)
//    private int questionId = 0;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "question")
    private String questionText;

    /*
    @NonNull
    @ColumnInfo(name = "possible_answers")
    private Answer possibleAnswers;


    @ColumnInfo(name = "recorded_answers")
    private String recordedAnswers;

    @ColumnInfo(name = "cause")
    private boolean questionIsCause;

    @ColumnInfo(name = "effect")
    private boolean questionIsEffect;
*/

/*
    //använd denna för att kunna ta emot svaret från en notification och tilldela svaret till den här frågan.
    private UUID questionId;

    private Notification notification;

    //hur sker upprepning?
    private Calendar calendar;

    //Ska denna sättas från auktorisationen eller ska jag ha en model med User som har ett antal Questions?
    private UUID questionOwner;
*/

    public Question(@NonNull String questionText) {
        this.questionText = questionText;
    }

//    public boolean autoGenerate() {
//        return true;
//    }
//
//    public int getQuestionId() {
//        return questionId;
//    }
//
//    public void setQuestionId(int questionId) {
//        this.questionId = questionId;
//    }

    @NonNull
    public String getQuestionText() {
        return questionText;
    }

}
