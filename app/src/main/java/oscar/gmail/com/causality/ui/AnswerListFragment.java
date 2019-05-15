package oscar.gmail.com.causality.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import oscar.gmail.com.causality.R;
import oscar.gmail.com.causality.models.AnswerViewModel;
import oscar.gmail.com.causality.repository.Answer;

/**
 *
 */
public class AnswerListFragment extends Fragment {
    private final String TAG = "causalityapp";

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private AnswerViewModel answerViewModel;
    private List<Answer> upToDateListOfAnswers = new ArrayList<>();

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AnswerListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AnswerListFragment newInstance(int columnCount) {
        AnswerListFragment fragment = new AnswerListFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_answer_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            answerViewModel = ((MainActivity) getActivity()).getAnswerViewModel();
            upToDateListOfAnswers = answerViewModel.getAllAnswers();

            List<Answer> toBeListed = new ArrayList<>();

            upToDateListOfAnswers.forEach(answer -> {
                if (answer.getFkQuestionId().equals(answerViewModel.getTempQuestion().getId())) {
                    toBeListed.add(answer);
                }
            });
            recyclerView.setAdapter(new AnswerRecyclerViewAdapter(toBeListed));

        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
