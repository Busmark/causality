package oscar.gmail.com.causality.question;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import oscar.gmail.com.causality.R;


public class QuestionListAdapter extends RecyclerView.Adapter<QuestionListAdapter.QuestionViewHolder> {

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionItemView;

        private QuestionViewHolder(View itemView) {
            super(itemView);
            questionItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Question> mQuestions = Collections.emptyList();

    public QuestionListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Question current = mQuestions.get(position);
        holder.questionItemView.setText(current.getQuestionText());
    }

    public void setQuestions(List<Question> questions) {
        mQuestions = questions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mQuestions.size();
    }
}
