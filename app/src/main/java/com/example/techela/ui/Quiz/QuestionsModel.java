package com.example.techela.ui.Quiz;

import java.util.ArrayList;

public class QuestionsModel {
    private int QuestionID;
    private String QuestionString;
    private ArrayList<String> options;

    public int getQuestionID() {
        return QuestionID;
    }

    public String getQuestionString() {
        return QuestionString;
    }

    public QuestionsModel(int QuestionID, String QuestionString, ArrayList<String> options) {
        this.QuestionID = QuestionID;
        this.QuestionString = QuestionString;
        this.options = options;
    }

    public ArrayList<String> getOptions() {
        return options;
    }
}
