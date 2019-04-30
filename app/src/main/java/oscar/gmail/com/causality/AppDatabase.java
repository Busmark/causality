package oscar.gmail.com.causality;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import oscar.gmail.com.causality.question.Question;
import oscar.gmail.com.causality.question.QuestionDao;


@Database(entities = {Question.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();

    // marking the instance as volatile to ensure atomic access to the variable
    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        final String TAG = "app";


        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "AppDatabase")
                            // Wipes and rebuilds instead of migrating if no Migration object.
                            // Migration is not part of this codelab.
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
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
            new AppDatabase.PopulateDbAsync(INSTANCE).execute();
        }
    };

    /**
     * Populate the AppDatabase in the background.
     * If you want to start with more questions, just add them.
     */
    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private final String TAG = "app";

        private final QuestionDao mDao;

        PopulateDbAsync(AppDatabase db) {
            mDao = db.questionDao();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean AppDatabase every time.
            // Not needed if you only populate on creation.
            mDao.deleteAll();

            Question question = new Question(true, "CAUSE", "När gick jag till sängs igår?");
            mDao.insert(question);
            question = new Question(true, "EFFECT", "Kände jag mig utvilad?");
            mDao.insert(question);
            return null;
        }
    }
}
