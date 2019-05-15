package oscar.gmail.com.causality.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.repository.Answer;

class AnswerRecyclerViewAdapter extends RecyclerView.Adapter<AnswerRecyclerViewAdapter.ViewHolder> {
    private final String TAG = "causalityapp";

    private List<Answer> answers; // Cached copy of answers

    /**
     * If no answers is in list the user gets a Toast.
     * @param items A list of questions to be displayed.
     * @param context The parent context for Toast.
     */
    public AnswerRecyclerViewAdapter(List<Answer> items, Context context) {
        answers = items;

        if(answers.size() == 0) {
            Toast toast = Toast.makeText(context ,"No answers to show", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }
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
     * Binds an onClickListener to each fragment_question and runs through the class member list of objects.
     * Dont get triggered if the question has no answers because the list is empty and cant be recycled through.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final AnswerRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.mItem = answers.get(position);

        CharSequence ch = answers.get(position).getAlarmDate() + " - " + answers.get(position).getAnswerText();

        holder.mContentView.setText(ch);


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
