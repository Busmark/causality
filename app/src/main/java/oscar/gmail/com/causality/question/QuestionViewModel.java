package oscar.gmail.com.causality.question;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import oscar.gmail.com.causality.services.AlarmJobService;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

// Using LiveData and caching what getAlphabetizedQuestionss returns has several benefits:
// - We can put an observer on the data (instead of polling for changes) and only update the
//   the UI when the data actually changes.
// - CausalityRepository is completely separated from the UI through the ViewModel.
public class QuestionViewModel extends AndroidViewModel {

    private final String TAG = "causalityapp";


    private QuestionRepository mRepository;
    private LiveData<Integer> insertResult;
    private LiveData<List<Question>> questionList;


    public QuestionViewModel(Application application) {
        super(application);
        mRepository = new QuestionRepository(application);
        this.insertResult = mRepository.getInsertResult();

        this.questionList = mRepository.getQuestionList();

//        getQuestionList().observeForever(questions -> {
//            Log.i(TAG, "size = " + questions.size());
//        });

//        getInsertResult().observeForever(integer -> Log.i(TAG, "Result = " + integer));
    }

    public String insert(Question question) {

        mRepository.insert(question);
        return question.getId();
    }

    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public LiveData<List<Question>> getQuestionList() {
        return questionList;
    }


    public void saveQuestion(Context context, String text, String notification_hour, String notification_mins, String notification_time, String notification_reps) {
        int reps = Integer.parseInt(notification_reps);

        //vid endast 1 fråga ska notisen komma direkt.
        if(reps == 1) {
            notification_time = "0800";
        }
        String qID = insert(new Question(text, notification_time));

        for(int i = 0; i < reps; i++)  {
            scheduleJob(context, text, qID, notification_time);
        }
    }

    //används för att skapa X alarm
    public void scheduleJob(Context context, String questionText, String questionId, String alarm) {



        PersistableBundle stringsToBeAdded = new PersistableBundle();
        stringsToBeAdded.putString("text", questionText);
        stringsToBeAdded.putString("id", questionId);
        stringsToBeAdded.putString("alarm", alarm);

        ComponentName componentName = new ComponentName(context, AlarmJobService.class);
        JobInfo info = new JobInfo.Builder(Integer.parseInt(alarm), componentName)
                .setRequiresCharging(true)
                .setMinimumLatency(1)
                .setOverrideDeadline(1)
                .setExtras(stringsToBeAdded)
                .build();
        // för loggning
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = jobScheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.i(TAG, "Main: Job Scheduled success");
        } else {
            Log.i(TAG, "Main: Job Scheduling failed");
        }
    }

//    public void printAllQuestions(View view) {
//        mAllQuestions.getValue().forEach(question -> Log.i(TAG, question.getQuestionText()));
//
//    }
//
//    public String[] getQuestionTextArray() {
//        String[] texts = new String[mAllQuestions.getValue().size()];
//        for (int i = 0; i < mAllQuestions.getValue().size(); i++) {
//            texts[i] = mAllQuestions.getValue().get(i).getQuestionText();
//        }
//        return texts;
//    }
//
//    public void getQuestionId(int checked) {
//    }
}

