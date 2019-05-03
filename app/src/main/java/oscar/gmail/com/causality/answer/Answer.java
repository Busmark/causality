package oscar.gmail.com.causality.answer;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

import oscar.gmail.com.causality.question.Question;

/*
foreignKeys = {
                @ForeignKey(entity = ProductEntity.class,
                        parentColumns = "id",
                        childColumns = "productId",
                        onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = "productId")
        })
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
