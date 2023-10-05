package com.example.checkerslab_edulearning.commonActivityPackage;

public class AllAssessmentModel {

    String assName,assMarks;

    public AllAssessmentModel(String assName, String assMarks) {
        this.assName = assName;
        this.assMarks = assMarks;
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
}
