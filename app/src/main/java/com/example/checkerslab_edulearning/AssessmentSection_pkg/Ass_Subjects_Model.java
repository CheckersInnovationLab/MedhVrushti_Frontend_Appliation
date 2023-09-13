package com.example.checkerslab_edulearning.AssessmentSection_pkg;

public class Ass_Subjects_Model {


    int subjectId,totalChapters;
    String subjectCode,subjectName;
    public Ass_Subjects_Model() {
    }

    public Ass_Subjects_Model(int subjectId, int totalChapters, String subjectCode, String subjectName) {
        this.subjectId = subjectId;
        this.totalChapters = totalChapters;
        this.subjectCode = subjectCode;
        this.subjectName = subjectName;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    public int getTotalChapters() {
        return totalChapters;
    }

    public void setTotalChapters(int totalChapters) {
        this.totalChapters = totalChapters;
    }

    public String getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(String subjectCode) {
        this.subjectCode = subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
