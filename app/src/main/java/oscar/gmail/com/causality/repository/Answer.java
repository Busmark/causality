package oscar.gmail.com.causality.repository;

import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

/**
 *
 */
@Entity(tableName = "answer_table",
        foreignKeys = {
                        @ForeignKey(entity = Question.class,
                                    parentColumns = "question_id",
                                    childColumns = "fk_question_id",
                                    onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "fk_question_id")
        })
public class Answer {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "answer_id")
    private String id;

    @ColumnInfo(name = "alarm_date")
    private String alarmDate;

    @ColumnInfo(name = "fk_question_id")
    public String fkQuestionId;

    @ColumnInfo(name = "answer_text")
    private String answerText;

    /**
     *
     * @param fkQuestionId
     * @param answerText
     */
    public Answer(@NonNull String fkQuestionId, String answerText, String alarmDate) {
        this.id = UUID.randomUUID().toString();
        this.fkQuestionId = fkQuestionId;
        this.answerText = answerText;
        this.alarmDate = alarmDate;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getFkQuestionId() {
        return fkQuestionId;
    }

    public void setFkQuestionId(String fkQuestionId) {
        this.fkQuestionId = fkQuestionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public String getAlarmDate() {
        return alarmDate;
    }

    public void setAlarmDate(String alarmDate) {
        this.alarmDate = alarmDate;
    }
}
