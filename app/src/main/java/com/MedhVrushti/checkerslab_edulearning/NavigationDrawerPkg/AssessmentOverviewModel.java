package com.MedhVrushti.checkerslab_edulearning.NavigationDrawerPkg;

public class AssessmentOverviewModel {


    String questionNumber,questionLatex,answerOverview,answerImg,totalMarks,obtainedMarks;

    public AssessmentOverviewModel(String questionNumber, String questionLatex, String answerOverview, String answerImg, String totalMarks, String obtainedMarks) {
        this.questionNumber = questionNumber;
        this.questionLatex = questionLatex;
        this.answerOverview = answerOverview;
        this.answerImg = answerImg;
        this.totalMarks = totalMarks;
        this.obtainedMarks = obtainedMarks;
    }

    public String getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(String questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionLatex() {
        return questionLatex;
    }

    public void setQuestionLatex(String questionLatex) {
        this.questionLatex = questionLatex;
    }

    public String getAnswerOverview() {
        return answerOverview;
    }

    public void setAnswerOverview(String answerOverview) {
        this.answerOverview = answerOverview;
    }

    public String getAnswerImg() {
        return answerImg;
    }

    public void setAnswerImg(String answerImg) {
        this.answerImg = answerImg;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getObtainedMarks() {
        return obtainedMarks;
    }

    public void setObtainedMarks(String obtainedMarks) {
        this.obtainedMarks = obtainedMarks;
    }
}
