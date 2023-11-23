package com.example.checkerslab_edulearning.commonActivityPackage;

public class AllAssessmentModel {

    String assName,assMarks,assId;

    public AllAssessmentModel(String assName, String assMarks, String assId) {
        this.assName = assName;
        this.assMarks = assMarks;
        this.assId = assId;
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
}
