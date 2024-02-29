package com.example.checkerslab_edulearning.AssessmentSection_pkg;

public class Selected_Test_Data_Model {

    int questionId,marks;
    String questionType,question,option1,option2,option3,option4,answer,answerDescription,status,selectedAnswer,questionDiagrams,descriptionDiagrams,questionTypeId;
    Boolean bookmarkStatus;


    public Selected_Test_Data_Model() {
    }

    public Selected_Test_Data_Model(int questionId, int marks, String questionType, String question, String option1, String option2, String option3, String option4, String answer, String answerDescription, String status, String selectedAnswer, String questionDiagrams, String descriptionDiagrams, String questionTypeId, Boolean bookmarkStatus) {
        this.questionId = questionId;
        this.marks = marks;
        this.questionType = questionType;
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
        this.answerDescription = answerDescription;
        this.status = status;
        this.selectedAnswer = selectedAnswer;
        this.questionDiagrams = questionDiagrams;
        this.descriptionDiagrams = descriptionDiagrams;
        this.questionTypeId = questionTypeId;
        this.bookmarkStatus = bookmarkStatus;
    }

    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }


    public String getQuestionDiagrams() {
        return questionDiagrams;
    }

    public void setQuestionDiagrams(String questionDiagrams) {
        this.questionDiagrams = questionDiagrams;
    }

    public String getDescriptionDiagrams() {
        return descriptionDiagrams;
    }

    public void setDescriptionDiagrams(String descriptionDiagrams) {
        this.descriptionDiagrams = descriptionDiagrams;
    }

    public String getQuestionTypeId() {
        return questionTypeId;
    }

    public void setQuestionTypeId(String questionTypeId) {
        this.questionTypeId = questionTypeId;
    }

    public Boolean getBookmarkStatus() {
        return bookmarkStatus;
    }

    public void setBookmarkStatus(Boolean bookmarkStatus) {
        this.bookmarkStatus = bookmarkStatus;
    }
}
