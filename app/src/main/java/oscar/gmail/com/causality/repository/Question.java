package oscar.gmail.com.causality.repository;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.UUID;

/**
 *
 */
@Entity(tableName = "question_table")
public class Question {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "question_id")
    private String id;

    @NonNull
    private boolean isActive = true;

    @NonNull
    @ColumnInfo(name = "question_text")
    private String questionText;

    @NonNull
    @ColumnInfo(name = "notification_time") //TT:mm
    private String notificationTime;

    public Question(@NonNull String questionText, String notificationTime) {
        id = UUID.randomUUID().toString();
        this.questionText = questionText;
        this.notificationTime = notificationTime;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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
