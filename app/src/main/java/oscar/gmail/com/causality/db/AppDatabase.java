package oscar.gmail.com.causality.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import oscar.gmail.com.causality.AppExecutors;
import oscar.gmail.com.causality.db.dao.CauseDao;
import oscar.gmail.com.causality.db.dao.EffectDao;
import oscar.gmail.com.causality.db.entity.CauseEntity;
import oscar.gmail.com.causality.db.entity.EffectEntity;


@Database(entities = {CauseEntity.class, EffectEntity.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase sInstance;

    @VisibleForTesting
    public static final String DATABASE_NAME = "basic-sample-db";

    public abstract CauseDao causeDao();

    public abstract EffectDao effectDao();

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    /**
     * Build the database. {@link Builder#build()} only sets up the database configuration and
     * creates a new instance of the database.
     * The SQLite database is only created when it's accessed for the first time.
     */
    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
//                        executors.diskIO().execute(() -> {
//                            // Add a delay to simulate a long-running operation
//                            // Generate the data for pre-population
//                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
//                            List<EffectEntity> effects = DataGenerator.generateProducts();
//                            List<CauseEntity> causes =
//                                    DataGenerator.generateCommentsForProducts(products);
//
//                            insertData(database, products, comments);
//                            // notify that the database was created and it's ready to be used
//                            database.setDatabaseCreated();
//                        });
                    }
                })
//                .addMigrations(MIGRATION_1_2)
                .build();
    }
    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }

//    private static void insertData(final AppDatabase database, final List<EffectEntity> products,
//                                   final List<CauseEntity> comments) {
//        database.runInTransaction(() -> {
//            database.effectDao().insertAll(products);
//            database.causeDao().insertAll(comments);
//        });
//    }


    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

//    private static final Migration MIGRATION_1_2 = new Migration(1, 2) {
//
//        @Override
//        public void migrate(@NonNull SupportSQLiteDatabase database) {
//            database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS `productsFts` USING FTS4("
//                    + "`name` TEXT, `description` TEXT, content=`products`)");
//            database.execSQL("INSERT INTO productsFts (`rowid`, `name`, `description`) "
//                    + "SELECT `id`, `name`, `description` FROM products");
//
//        }
//    };
}
