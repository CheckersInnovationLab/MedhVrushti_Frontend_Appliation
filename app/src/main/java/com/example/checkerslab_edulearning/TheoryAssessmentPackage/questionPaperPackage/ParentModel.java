package com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage;

import android.widget.Toast;

import java.util.List;

public class ParentModel {

    String mainQuestion,mainQueTotalMarks,subQueMarks,MainQuestNo;
    List<ChildModel> subQuestionList;

//    public ParentModel(String mainQuestion, List<ChildModel> subQuestionList) {
//        this.mainQuestion = mainQuestion;
//        this.subQuestionList = subQuestionList;
//    }


    public ParentModel(String mainQuestion, String mainQueTotalMarks, String subQueMarks, String mainQuestNo, List<ChildModel> subQuestionList) {
        this.mainQuestion = mainQuestion;
        this.mainQueTotalMarks = mainQueTotalMarks;
        this.subQueMarks = subQueMarks;
        MainQuestNo = mainQuestNo;
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

    public String getMainQueTotalMarks() {
        return mainQueTotalMarks;
    }

    public void setMainQueTotalMarks(String mainQueTotalMarks) {
        this.mainQueTotalMarks = mainQueTotalMarks;
    }

    public String getSubQueMarks() {
        return subQueMarks;
    }

    public void setSubQueMarks(String subQueMarks) {
        this.subQueMarks = subQueMarks;
    }

    public String getMainQuestNo() {
        return MainQuestNo;
    }

    public void setMainQuestNo(String mainQuestNo) {
        MainQuestNo = mainQuestNo;
    }
}
