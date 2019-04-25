package oscar.gmail.com.causality.effect;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import oscar.gmail.com.causality.R;

/*
Används för att skapa upp flera lika poster i UI´t.
 */
public class EffectListAdapter extends RecyclerView.Adapter<EffectListAdapter.EffectViewHolder> {

    class EffectViewHolder extends RecyclerView.ViewHolder {
        private final TextView effectItemView;

        private EffectViewHolder(View itemView) {
            super(itemView);
            effectItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<Effect> mEffects = Collections.emptyList();

    public EffectListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public EffectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new EffectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(EffectViewHolder holder, int position) {
        Effect current = mEffects.get(position);
        holder.effectItemView.setText(current.getText());
    }

    public void setEffects(List<Effect> effects) {
        mEffects = effects;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mEffects.size();
    }
}
