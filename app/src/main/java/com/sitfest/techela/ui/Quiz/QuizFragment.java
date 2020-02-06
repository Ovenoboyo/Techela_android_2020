package com.sitfest.techela.ui.Quiz;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.sitfest.techela.MainActivity;
import com.sitfest.techela.R;
import com.sitfest.techela.ui.home.HomeFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class QuizFragment extends Fragment implements View.OnClickListener {

    private Map<Integer, QuestionsModel> QuestionsMap = new HashMap<>();
    private final int LAST_POSITION = 9; // 10 Questions
    private final int FIRST_POSITION = 0;
    private final Map<String, String> AnswersMap = new HashMap<>();
    private Button start;
    private Button next;
    private Button prev;
    private Button submit;
    private LinearLayout options_ll;
    private LinearLayout question_buttons_ll;
    private RadioButton option0;
    private RadioButton option1;
    private RadioButton option2;
    private RadioButton option3;
    private RadioGroup group;
    private TextView quiz_title, quiz_holder;
    private RelativeLayout end_quiz;
    private Button end_button;
    private int position = 0;
    private final ArrayList<String> keys = new ArrayList<>();
    private View root;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((MainActivity) Objects.requireNonNull(getActivity())).setDrawerEnabled(true);
    }

    public QuizFragment() {
        // Required empty public constructor
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.quiz_fragment, container, false);
        Toolbar toolbar = ((MainActivity) Objects.requireNonNull(getActivity())).getToolbar();
        toolbar.setTitle("Quiz");

        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getContext());
            mProgressDialog.setMessage("Getting data...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference statusNode = rootRef.child("QuizStart");
        statusNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("test", "onDataChange: " + Boolean.valueOf(dataSnapshot.getValue().toString()));
                if (Boolean.valueOf(dataSnapshot.getValue().toString())) {
                    quiz_title = root.findViewById(R.id.quiz_title);
                    end_quiz = root.findViewById(R.id.quiz_end);
                    end_button = root.findViewById(R.id.end_button);
                    start = root.findViewById(R.id.btn_start);
                    start.setVisibility(View.GONE);
                    next = root.findViewById(R.id.btn_next);
                    prev = root.findViewById(R.id.btn_prev);
                    submit = root.findViewById(R.id.submit);

                    options_ll = root.findViewById(R.id.options_ll);
                    option0 = root.findViewById(R.id.option0);
                    option1 = root.findViewById(R.id.option1);
                    option2 = root.findViewById(R.id.option2);
                    option3 = root.findViewById(R.id.option3);
                    group = root.findViewById(R.id.radiogrp);

                    PopulateQuestions();

                    option0.setOnClickListener(QuizFragment.this);
                    option1.setOnClickListener(QuizFragment.this);
                    option2.setOnClickListener(QuizFragment.this);
                    option3.setOnClickListener(QuizFragment.this);

                    question_buttons_ll = root.findViewById(R.id.question_buttons);

                    quiz_holder = root.findViewById(R.id.event_holder);

                    quiz_holder.setVisibility(View.GONE);

                    start.setOnClickListener(QuizFragment.this);
                    next.setOnClickListener(QuizFragment.this);
                    prev.setOnClickListener(QuizFragment.this);
                    submit.setOnClickListener(QuizFragment.this);

                    quiz_title.setVisibility(View.VISIBLE);
                    quiz_title.setText("Start Quiz?");

                    end_button.setOnClickListener(v -> {
                        HomeFragment nextFrag = new HomeFragment();
                        QuizFragment.this.getActivity().getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_in_left,
                                        android.R.anim.slide_out_right, android.R.anim.slide_out_right )
                                .replace(R.id.nav_host_fragment, nextFrag, null)
                                .addToBackStack(null)
                                .commit();
                    });

                } else {
                    if (mProgressDialog != null && mProgressDialog.isShowing()) {
                        mProgressDialog.dismiss();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return root;
    }

    private void checkEligiblity() {
        // Tribute to KJ
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference statusNode = rootRef.child("QuizAnswers");
        DatabaseReference userNode = statusNode.child(Objects.requireNonNull(user).getUid());


        userNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.getChildrenCount() < 1) {
                    setupQuestions();
                    DisplayQuestion(position);
                } else {
                    Toast.makeText(QuizFragment.this.getContext(), "You have already attempted the quiz", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void setupQuestions() {
        start.setVisibility(View.GONE);
        options_ll.setVisibility(View.VISIBLE);
        question_buttons_ll.setVisibility(View.VISIBLE);
    }

    private void DisplayQuestion (int position) {
        try {
            quiz_title.setText("Q. " + Objects.requireNonNull(QuestionsMap.get(position)).getQuestionString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> options = Objects.requireNonNull(QuestionsMap.get(position)).getOptions();



        group.clearCheck();

        Log.d("test", "DisplayQuestion: "+option0.getText());

        option0.setText(options.get(0));
        option1.setText(options.get(1));
        option2.setText(options.get(2));
        option3.setText(options.get(3));

        if (option0.getText().equals(AnswersMap.get(Objects.requireNonNull(QuestionsMap.get(position)).getQuestionID()))) {
            option0.setChecked(true);
        } else if(option1.getText().equals(AnswersMap.get(Objects.requireNonNull(QuestionsMap.get(position)).getQuestionID()))) {
            option1.setChecked(true);
        } else if (option2.getText().equals(AnswersMap.get(Objects.requireNonNull(QuestionsMap.get(position)).getQuestionID()))) {
            option2.setChecked(true);
        } else if (option3.getText().equals(AnswersMap.get(Objects.requireNonNull(QuestionsMap.get(position)).getQuestionID()))) {
            option3.setChecked(true);
        }

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
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
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    private void PopulateQuestions() {
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference questionsNode = database.getReference("Quiz");
        questionsNode.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot questionNo : dataSnapshot.getChildren()) {
                    keys.add(questionNo.getKey());
                    String question = "";
                    ArrayList<String> options = new ArrayList<>();
                    for (DataSnapshot details : questionNo.getChildren()) {
                        if (Objects.requireNonNull(details.getKey()).compareTo("question") == 0) {
                            question = Objects.requireNonNull(details.getValue()).toString();
                        }
                        if (details.getKey().matches(".*option.*")) {
                            options.add(Objects.requireNonNull(details.getValue()).toString());
                        }
                    }
                    if (options.size() == 4) {
                        QuestionsModel model = new QuestionsModel(questionNo.getKey(), question, options);
                        Log.d("test", "onDataChange: "+model);
                        QuestionsMap.put(i, model);
                    } else if (options.size() < 4){
                        for (int j = options.size() - 1; j < 4; j++) {
                            options.add(j, " ");
                        }
                        QuestionsModel model = new QuestionsModel(questionNo.getKey(), question, options);
                        Log.d("test", "onDataChange: "+model);
                        QuestionsMap.put(i, model);
                    }

                    Log.d("test", "onDataChange: "+options);

                    i++;
                }
                Map<Integer, QuestionsModel> finalMap = new HashMap<>();
                Log.d("test", "onDataChange: "+QuestionsMap);
                Random rand = new Random();
                Object[] keysArray = QuestionsMap.keySet().toArray();
                ArrayList<Object> keysList = new ArrayList<>(Arrays.asList(keysArray));
                for (int k =  0; k < 10; k++) {
                    int random;
                    if (keysList.size() > 1) {
                        random = rand.nextInt(keysList.size() - 1);
                    } else {
                        random = 0;
                    }
                    Log.d("test", "onDataChange: "+random);
                    finalMap.put(Integer.valueOf(keysList.get(random).toString()), QuestionsMap.get(Integer.valueOf(keysList.get(random).toString())));
                    keysList.remove(random);

                }
                QuestionsMap = finalMap;
                Log.d("test", "onDataChange: "+QuestionsMap);
                start.setVisibility(View.VISIBLE);
                if (mProgressDialog != null && mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveOption(int position) {
        RadioButton button = root.findViewById(group.getCheckedRadioButtonId());
        AnswersMap.put(Objects.requireNonNull(QuestionsMap.get(position)).getQuestionID(), button.getText().toString());
        Log.d("test", "saveOption: "+AnswersMap);
    }

    private void submit() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference statusNode = rootRef.child("QuizAnswers");
        DatabaseReference userNode = statusNode.child(Objects.requireNonNull(user).getUid());
        userNode.setValue(AnswersMap);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.option0:
            case R.id.option3:
            case R.id.option2:
            case R.id.option1:
                saveOption(position);
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
                checkEligiblity();
                break;
            case R.id.submit:
                submit();
                endQuiz();
                break;
        }
        updateButtons();
    }

    private void endQuiz() {
        start.setVisibility(View.GONE);
        options_ll.setVisibility(View.GONE);
        question_buttons_ll.setVisibility(View.GONE);
        quiz_title.setVisibility(View.GONE);
        end_quiz.setVisibility(View.VISIBLE);
    }
}
