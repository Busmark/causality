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


public class EffectListAdapter extends RecyclerView.Adapter<EffectListAdapter.QuestionViewHolder> {

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        private final TextView questionItemView;

        private QuestionViewHolder(View itemView) {
            super(itemView);
            questionItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Effect> mEffects = Collections.emptyList();

    public EffectListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new QuestionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Effect current = mEffects.get(position);
        holder.questionItemView.setText(current.getQuestionText());
    }

    public void setQuestions(List<Effect> effects) {
        mEffects = effects;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mEffects.size();
    }
}
