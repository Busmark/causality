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

import java.util.List;

import oscar.gmail.com.causality.answer.Answer;
import oscar.gmail.com.causality.answer.AnswerRepository;
import oscar.gmail.com.causality.services.AlarmJobService;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class QuestionViewModel extends AndroidViewModel {
    private final String TAG = "causalityapp";


    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;

    //handles feedback from saving a Question
    private LiveData<Integer> insertResult;
    private LiveData<List<Question>> questionList;
//    private LiveData<List<Answer>> mAllAnswers;


    public QuestionViewModel(Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
//        mAllAnswers = answerRepository.getAllAnswers();

        this.insertResult = questionRepository.getInsertResult();

        this.questionList = questionRepository.getQuestionList();

        //krävs en observer för att questionList ska lyssna.
        getQuestionList().observeForever(questions -> {
        });



//        getInsertResult().observeForever(integer -> Log.i(TAG, "Result = " + integer));
    }

    public String insert(Question question) {

        questionRepository.insert(question);
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
}

