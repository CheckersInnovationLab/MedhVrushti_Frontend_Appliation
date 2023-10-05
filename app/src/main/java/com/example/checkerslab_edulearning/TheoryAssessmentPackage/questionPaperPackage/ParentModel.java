package com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage;

import android.widget.Toast;

import java.util.List;

public class ParentModel {

    String mainQuestion;
    List<ChildModel> subQuestionList;

    public ParentModel(String mainQuestion, List<ChildModel> subQuestionList) {
        this.mainQuestion = mainQuestion;
        this.subQuestionList = subQuestionList;
    }

    public ParentModel() {
    }

    public String getMainQuestion() {
        return mainQuestion;
    }

    public void setMainQuestion(String mainQuestion) {
        this.mainQuestion = mainQuestion;
    }

    public List<ChildModel> getSubQuestionList() {
        return subQuestionList;
    }

    public void setSubQuestionList(List<ChildModel> subQuestionList) {
        this.subQuestionList = subQuestionList;
    }
}
