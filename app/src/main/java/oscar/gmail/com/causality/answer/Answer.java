package oscar.gmail.com.causality.answer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import oscar.gmail.com.causality.question.Question;

@Entity(tableName = "answer")
public class Answer {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "answer_id")
    private int answerId = 0;

    @NonNull
    @ColumnInfo(name = "answer_value")
    private String answerValue = "";

    @ForeignKey(entity = Question.class, parentColumns = "question", childColumns = "question")
    @NonNull
    @ColumnInfo(name = "question")
    private Question question;

    public Answer(int answerId, @NonNull String answerValue, @NonNull Question question) {
        this.answerId = answerId;
        this.answerValue = answerValue;
        this.question = question;
    }

    public int getAnswerId() {
        return answerId;
    }

    @NonNull
    public String getAnswerValue() {
        return answerValue;
    }

    public void setAnswerValue(@NonNull String answerValue) {
        this.answerValue = answerValue;
    }

    @NonNull
    public Question getQuestion() {
        return question;
    }

    public void setQuestion(@NonNull Question question) {
        this.question = question;
    }
}
