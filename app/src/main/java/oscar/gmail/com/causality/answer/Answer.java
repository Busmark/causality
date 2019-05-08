package oscar.gmail.com.causality.answer;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import java.util.UUID;

import oscar.gmail.com.causality.question.Question;


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

    @ColumnInfo(name = "fk_question_id")
    public String fkQuestionId;

    @ColumnInfo(name = "answer_text")
    private String answerText;

    public Answer(@NonNull String fkQuestionId, String answerText) {
        this.id = UUID.randomUUID().toString();
        this.fkQuestionId = fkQuestionId;
        this.answerText = answerText;
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
}
