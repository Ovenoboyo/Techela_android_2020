package com.example.techela.ui.Quiz;

import android.util.Log;

import java.util.ArrayList;

public class QuestionsModel {
    private String QuestionID;
    private String QuestionString;
    private ArrayList<String> options;

    public String getQuestionID() {
        return QuestionID;
    }

    public String getQuestionString() {
        return QuestionString;
    }

    public QuestionsModel(String QuestionID, String QuestionString, ArrayList<String> options) {
        Log.d("test", "QuestionsModel: "+options);
        this.QuestionID = QuestionID;
        this.QuestionString = QuestionString;
        this.options = options;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
}
