package com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage;

public class ChildModel {
    String subQuestionId,subQuestion,subQuestionType,option1,option2,option3,option4,quesMarks;

    public static final int LayoutOne = 0;
    public static final int LayoutTwo = 1;
    private int viewType;


//    public ChildModel(String subQuestion, String subQuestionType, String option1, String option2, String option3, String option4) {
//        this.subQuestion = subQuestion;
//        this.subQuestionType = subQuestionType;
//        this.option1 = option1;
//        this.option2 = option2;
//        this.option3 = option3;
//        this.option4 = option4;
//    }


    public ChildModel(String subQuestionId, String subQuestion, String subQuestionType, String option1, String option2, String option3, String option4, String quesMarks, int viewType) {
        this.subQuestionId = subQuestionId;
        this.subQuestion = subQuestion;
        this.subQuestionType = subQuestionType;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.quesMarks = quesMarks;
        this.viewType = viewType;
    }

    public ChildModel() {
    }

    public String getSubQuestion() {
        return subQuestion;
    }

    public void setSubQuestion(String subQuestion) {
        this.subQuestion = subQuestion;
    }

    public String getSubQuestionType() {
        return subQuestionType;
    }

    public void setSubQuestionType(String subQuestionType) {
        this.subQuestionType = subQuestionType;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getSubQuestionId() {
        return subQuestionId;
    }

    public void setSubQuestionId(String subQuestionId) {
        this.subQuestionId = subQuestionId;
    }

    public String getQuesMarks() {
        return quesMarks;
    }

    public void setQuesMarks(String quesMarks) {
        this.quesMarks = quesMarks;
    }
}
