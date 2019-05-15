package oscar.gmail.com.causality.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.repository.Answer;

class AnswerRecyclerViewAdapter extends RecyclerView.Adapter<AnswerRecyclerViewAdapter.ViewHolder> {
    private final String TAG = "causalityapp";

    private List<Answer> answers; // Cached copy of words

    /**
     *
     * @param items A list of questions to be displayed.
     * @param
     */
    public AnswerRecyclerViewAdapter(List<Answer> items) {
        answers = items;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public AnswerRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_answer, parent, false);
        return new AnswerRecyclerViewAdapter.ViewHolder(view);
    }

    /**
     * Binds an onClickListener to each fragment_question
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final AnswerRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = answers.get(position);
        holder.mContentView.setText(answers.get(position).getAnswerText());
        holder.mView.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return answers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public Answer mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.answer_text);
        }

        /**
         *
         * @return
         */
        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
