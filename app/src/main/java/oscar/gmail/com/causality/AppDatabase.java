package oscar.gmail.com.causality;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import oscar.gmail.com.causality.repository.Answer;
import oscar.gmail.com.causality.repository.AnswerDao;
import oscar.gmail.com.causality.repository.Question;
import oscar.gmail.com.causality.repository.QuestionDao;

/**
 *
 */
@Database(entities = {Question.class, Answer.class}, version = 7, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();
    public abstract AnswerDao answerDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        final String TAG = "causalityapp";
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDatabase")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback) //tömmer db´n och fyller upp den med default data
                            .build();
                }
            }
        }
        return INSTANCE;
    }

//    /**
//     * Override the onOpen method to populate the AppDatabase.
//     * For this sample, we clear the AppDatabase every time it is created or opened.
//     *
//     * If you want to populate the AppDatabase only when the AppDatabase is created for the 1st time,
//     * override RoomDatabase.Callback()#onCreate
//     */
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            // If you want to keep the data through app restarts,
            // comment out the following line.
//            new AppDatabase.PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the AppDatabase in the background.
     * If you want to start with more questions, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final String TAG = "causalityapp";

        private final QuestionDao questionDao;
        private final AnswerDao answerDao;

        /**
         *
         * @param db
         */
        PopulateDbAsync(AppDatabase db) {
            questionDao = db.questionDao();
            answerDao = db.answerDao();
        }

        /**
         *
         * @param params
         * @return
         */
        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean AppDatabase every time.
            // Not needed if you only populate on creation.
            questionDao.deleteAll();
            answerDao.deleteAll();

            Question question1 = new Question("Vad åt jag till middag?",  "19:00");
            questionDao.insert(question1);
            Question question2 = new Question("Känner jag mig utvilad?", "09:00");
            questionDao.insert(question2);

            //hämta id på question
            String question1Id = questionDao.getQuestionId("Vad åt jag till middag?");
            Answer answer1 = new Answer(question1Id, "soppa", "2019-05-14"); // yyyy-MM-dd
            Answer answer2 = new Answer(question1Id, "fisk", "2019-05-13");
            Answer answer3 = new Answer(question1Id, "ost", "2019-05-12");

            answerDao.insert(answer1);
            answerDao.insert(answer2);
            answerDao.insert(answer3);

            return null;
        }
    }
}
