package com.MedhVrushti.checkerslab_edulearning.commonActivityPackage;

public class AllAssessmentModel {

    String assName,assMarks,assId,assStatus,totalMarks,totalQuestion;
    int totalTime;

    public AllAssessmentModel(String assName, String assMarks, String assId, String assStatus) {
        this.assName = assName;
        this.assMarks = assMarks;
        this.assId = assId;
        this.assStatus = assStatus;
    }

    public AllAssessmentModel(String assName, String assMarks, String assId, String assStatus, String totalQuestion, int totalTime) {
        this.assName = assName;
        this.assMarks = assMarks;
        this.assId = assId;
        this.assStatus = assStatus;
        this.totalMarks = totalMarks;
        this.totalQuestion = totalQuestion;
        this.totalTime = totalTime;
    }

    public AllAssessmentModel() {
    }

    public String getAssName() {
        return assName;
    }

    public void setAssName(String assName) {
        this.assName = assName;
    }

    public String getAssMarks() {
        return assMarks;
    }

    public void setAssMarks(String assMarks) {
        this.assMarks = assMarks;
    }

    public String getAssId() {
        return assId;
    }

    public void setAssId(String assId) {
        this.assId = assId;
    }

    public String getAssStatus() {
        return assStatus;
    }

    public void setAssStatus(String assStatus) {
        this.assStatus = assStatus;
    }

    public String getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(String totalMarks) {
        this.totalMarks = totalMarks;
    }

    public String getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(String totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }
}
