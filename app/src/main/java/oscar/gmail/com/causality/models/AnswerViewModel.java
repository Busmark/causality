package oscar.gmail.com.causality.models;

import android.app.Application;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import oscar.gmail.com.causality.repository.Answer;
import oscar.gmail.com.causality.repository.AnswerRepository;
import oscar.gmail.com.causality.repository.Question;

public class AnswerViewModel extends AndroidViewModel {

    private final String TAG = "causalityapp";

    private AnswerRepository answerRepository;
    private List<Answer> answers;
    private Question tempQuestion;

    /**
     *
     * @param application
     */
    public AnswerViewModel(Application application) {
        super(application);
        answerRepository = new AnswerRepository(application);
        //answers = answerRepository.getAllAnswers();

        answerRepository.getAllAnswers().observeForever(answers -> {
            this.answers = answers;
        });

    }

    /**
     *
     * @return
     */
    public List<Answer> getAllAnswers() {
        return answers;
    }

    public Question getTempQuestion() {
        return tempQuestion;
    }

    public void setTempQuestion(Question tempQuestion) {
        this.tempQuestion = tempQuestion;
    }

    public List<Answer> getAllAnswersForAQuestion(String questionId) {
        List<Answer> toBeReturned = new ArrayList<>();

        getAllAnswers().forEach(answer -> {
            if (answer.getFkQuestionId().equals(questionId)) {
                toBeReturned.add(answer);
            }
        });
      return toBeReturned;

    }

    /**
     *
     * @param answer
     */
    public void insert(Answer answer) {
        answerRepository.insert(answer);
    }

}
