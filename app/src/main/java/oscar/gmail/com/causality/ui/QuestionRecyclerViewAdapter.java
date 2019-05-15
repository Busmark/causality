package oscar.gmail.com.causality.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.repository.Question;
import oscar.gmail.com.causality.ui.QuestionListFragment.OnListFragmentInteractionListener;


public class QuestionRecyclerViewAdapter extends RecyclerView.Adapter<QuestionRecyclerViewAdapter.ViewHolder> {
    private final String TAG = "causalityapp";

    private final OnListFragmentInteractionListener mListener;
    private List<Question> questions; // Cached copy of words

    /**
     *
     * @param items A list of questions to be displayed.
     * @param listener
     */
    public QuestionRecyclerViewAdapter(List<Question> items, OnListFragmentInteractionListener listener) {
        questions = items;
        mListener = listener;
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_question, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Binds an onClickListener to each fragment_question
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = questions.get(position);
        holder.mContentView.setText(questions.get(position).getQuestionText());
        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);



            }
        });
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public Question mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.question_text);
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
