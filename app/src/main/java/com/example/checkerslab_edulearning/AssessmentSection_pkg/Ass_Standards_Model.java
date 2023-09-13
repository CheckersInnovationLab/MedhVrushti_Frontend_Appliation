package com.example.checkerslab_edulearning.AssessmentSection_pkg;

public class Ass_Standards_Model {

    int stdId,totalSubjects;
    String stdCode,stdName,stdImgUrl;

    public Ass_Standards_Model() {
    }

    public Ass_Standards_Model(int stdId, int totalSubjects, String stdCode, String stdName) {
        this.stdId = stdId;
        this.totalSubjects = totalSubjects;
        this.stdCode = stdCode;
        this.stdName = stdName;
    }

    public int getStdId() {
        return stdId;
    }

    public void setStdId(int stdId) {
        this.stdId = stdId;
    }

    public int getTotalSubjects() {
        return totalSubjects;
    }

    public void setTotalSubjects(int totalSubjects) {
        this.totalSubjects = totalSubjects;
    }

    public String getStdCode() {
        return stdCode;
    }

    public void setStdCode(String stdCode) {
        this.stdCode = stdCode;
    }

    public String getStdName() {
        return stdName;
    }

    public void setStdName(String stdName) {
        this.stdName = stdName;
    }

    public String getStdImgUrl() {
        return stdImgUrl;
    }

    public void setStdImgUrl(String stdImgUrl) {
        this.stdImgUrl = stdImgUrl;
    }
}
