package oscar.gmail.com.causality.question;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;


@Entity(tableName = "question_table")
public class Question {

    @PrimaryKey
    @NonNull
    private String QuestionId;

    @NonNull
    private boolean isActive = true;

    @NonNull
    @ColumnInfo(name = "question_text")
    private String questionText;

    @NonNull
    @ColumnInfo(name = "notification_time") //TT:mm
    private String notificationTime;

    public Question(@NonNull String questionText, String notificationTime) {
        QuestionId = UUID.randomUUID().toString();
        this.questionText = questionText;
        this.notificationTime = notificationTime;
    }

    @NonNull
    public String getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(@NonNull String questionId) {
        QuestionId = questionId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @NonNull
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(@NonNull String questionText) {
        this.questionText = questionText;
    }

    @NonNull
    public String getNotificationTime() {
        return notificationTime;
    }

    public void setNotificationTime(@NonNull String notificationTime) {
        this.notificationTime = notificationTime;
    }
}
