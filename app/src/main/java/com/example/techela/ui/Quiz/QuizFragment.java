package com.example.techela.ui.Quiz;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.techela.MainActivity;
import com.example.techela.R;
import com.example.techela.ui.home.RecyclerAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QuizFragment extends Fragment implements View.OnClickListener {

    Map<Integer, QuestionsModel> QuestionsMap = new HashMap<>();
    private int LAST_POSITION = 9; // 10 Questions
    private int FIRST_POSITION = 0;
    Map<Integer, Integer> AnswersMap = new HashMap<>();
    Button start, next, prev, submit;
    LinearLayout options_ll, question_buttons_ll;
    TextView quiz_title, option0, option1, option2, option3;
    private int position = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) getActivity()).setDrawerEnabled(true);
        PopulateQuestions();
    }

    public QuizFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.quiz_fragment, container, false);
        Toolbar toolbar = ((MainActivity)getActivity()).getToolbar();
        toolbar.setTitle("Quiz");

        quiz_title = root.findViewById(R.id.quiz_title);
        start = root.findViewById(R.id.btn_start);
        next = root.findViewById(R.id.btn_next);
        prev = root.findViewById(R.id.btn_prev);
        submit = root.findViewById(R.id.submit);

        options_ll = root.findViewById(R.id.options_ll);
        option0 = root.findViewById(R.id.option0);
        option1 = root.findViewById(R.id.option1);
        option2 = root.findViewById(R.id.option2);
        option3 = root.findViewById(R.id.option3);

        question_buttons_ll = root.findViewById(R.id.question_buttons);

        option0.setOnClickListener(this);
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        start.setOnClickListener(this);
        next.setOnClickListener(this);
        prev.setOnClickListener(this);
        submit.setOnClickListener(this);

        quiz_title.setText("Start Quiz?");

        return root;
    }

    private void setupQuestions() {
        start.setVisibility(View.GONE);
        options_ll.setVisibility(View.VISIBLE);
        question_buttons_ll.setVisibility(View.VISIBLE);
    }

    private void DisplayQuestion (int position) {
        Log.d("test", "DisplayQuestion: "+ position);
        quiz_title.setText(QuestionsMap.get(position).getQuestionString());
        ArrayList<String> options = QuestionsMap.get(position).getOptions();
        option0.setText(options.get(0));
        option1.setText(options.get(1));
        option2.setText(options.get(2));
        option3.setText(options.get(3));

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
    }

    private void updatePosition(boolean increment) {
        if (position >= LAST_POSITION) {
            if (increment) {
                position = LAST_POSITION;
            } else {
                position--;
            }
        } else if (position <= FIRST_POSITION) {
            if (!increment) {
                position = FIRST_POSITION;
            } else {
                position++;
            }
        } else {
            if (increment) {
                position++;
            } else {
                position--;
            }
        }
    }

    private void updateButtons() {
        if (position == LAST_POSITION) {
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
            submit.setVisibility(View.VISIBLE);
        } else if (position == FIRST_POSITION) {
            prev.setVisibility(View.GONE);
            next.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        } else {
            prev.setVisibility(View.VISIBLE);
            next.setVisibility(View.VISIBLE);
            submit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    private void PopulateQuestions() {
        for (int i = 0; i < 10; i++) {
            this.QuestionsMap.put(i, new QuestionsModel(i, "this is a question", new ArrayList<String>() {
                {
                    add("option0");
                    add("option1");
                    add("option2");
                    add("option3");
                }
            }));
        }
    }

    private void optionSelected(int position, int option, TextView selectedOption) {
        for (int i = 0; i < options_ll.getChildCount(); i++) {
            options_ll.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
        }
        selectedOption.setBackgroundColor(Color.BLUE);
        AnswersMap.put(position, option);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option0:
                optionSelected(position, 0, option0);
                break;
            case R.id.option1:
                optionSelected(position, 1, option1);
                break;
            case R.id.option2:
                optionSelected(position, 2, option2);
                break;
            case R.id.option3:
                optionSelected(position, 3, option3);
                break;
            case R.id.btn_next:
                updatePosition(true);
                DisplayQuestion(position);
                break;
            case R.id.btn_prev:
                updatePosition(false);
                DisplayQuestion(position);
                break;
            case R.id.btn_start:
                setupQuestions();
                DisplayQuestion(position);
                break;
        }
        updateButtons();
    }
}
