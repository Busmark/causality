package oscar.gmail.com.causality.models;

import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.PersistableBundle;
import android.util.Log;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import oscar.gmail.com.causality.repository.Question;
import oscar.gmail.com.causality.repository.QuestionRepository;
import oscar.gmail.com.causality.repository.services.AlarmJobService;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class QuestionViewModel extends AndroidViewModel {
    private final String TAG = "causalityapp";

    private QuestionRepository questionRepository;
    //private AnswerRepository answerRepository;

    //handles feedback from saving a Question
    private LiveData<Integer> insertResult;
    private int result;
    private LiveData<List<Question>> questionList;
    private List<Question> questions;

    /**
     *
     * @param application
     */
    public QuestionViewModel(Application application) {
        super(application);
        questionRepository = new QuestionRepository(application);
//        this.insertResult = questionRepository.getInsertResult();
//        this.questionList = questionRepository.getQuestionList();

        //listens to changes in database
        questionRepository.getQuestionList().observeForever(questions -> {
            this.questions = questions;
        });

        //listens to repo member saving success or fail from last save.
        questionRepository.getInsertResult().observeForever(insertResult -> {
            result = insertResult;
        });
    }

    public List<Question> getQuestions() {
        return questions;
    }

    /**
     *
     * @param question
     * @return The new questions id.
     */
    public String insert(Question question) {
        questionRepository.insert(question);
        return question.getId();
    }

    /**
     *
     * @return An 0 or 1 depending if the insert(question) was fail or success.
     */
    public LiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public LiveData<List<Question>> getQuestionList() {
        return questionList;
    }


    /**
     *
     * @param context
     * @param text
     * @param notification_time
     * @param notification_reps
     */
    public void saveQuestion(Context context, String text, String notification_time, String notification_reps) {
        int reps = Integer.parseInt(notification_reps);

        // for dev purposes, to trigger instant alarm
        if(reps == 1) {
            notification_time = "1000";
        }
        String questionId = insert(new Question(text, notification_time));

        scheduleJob(context, text, questionId, notification_time, reps);

    }

    /**
     * Service outside main thread that creates X alarms.
     * @param context
     * @param questionText  The question to be put in Notification.
     * @param questionId    Identifier to save to correct question.
     * @param alarm         Identifier for when alarm should go off.
     */
    public void scheduleJob(Context context, String questionText, String questionId, String alarm, int repetitions) {

        PersistableBundle stringsToBeAdded = new PersistableBundle();
        stringsToBeAdded.putString("text", questionText);
        stringsToBeAdded.putString("id", questionId);
        stringsToBeAdded.putString("alarm", alarm);
        stringsToBeAdded.putInt("repetitions", repetitions);

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
        } else {
            Log.i(TAG, "Model: Job Scheduling failed");
        }
    }
}

