package oscar.gmail.com.causality.answer;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Answer.class}, version = 1)
public abstract class AnswerDatabase extends RoomDatabase     {

    private static final String DB_NAME = "answerDatabase.db";
    private static volatile AnswerDatabase instance;

    static synchronized AnswerDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static AnswerDatabase create(final Context context) {
        return Room.databaseBuilder(
                context,
                AnswerDatabase.class,
                DB_NAME).build();

    }

    public abstract AnswerDao getAnswerDao();
}

