package com.example.checkerslab_edulearning.NavigationDrawerPkg;

public class myAssessmentModel {


    String user_assessmentID,assessmentName,checkingStatus,ass_end_date;

    public myAssessmentModel(String user_assessmentID, String assessmentName, String checkingStatus, String ass_end_date) {
        this.user_assessmentID = user_assessmentID;
        this.assessmentName = assessmentName;
        this.checkingStatus = checkingStatus;
        this.ass_end_date = ass_end_date;
    }

    public myAssessmentModel() {
    }

    public String getUser_assessmentID() {
        return user_assessmentID;
    }

    public void setUser_assessmentID(String user_assessmentID) {
        this.user_assessmentID = user_assessmentID;
    }

    public String getAssessmentName() {
        return assessmentName;
    }

    public void setAssessmentName(String assessmentName) {
        this.assessmentName = assessmentName;
    }

    public String getCheckingStatus() {
        return checkingStatus;
    }

    public void setCheckingStatus(String checkingStatus) {
        this.checkingStatus = checkingStatus;
    }

    public String getAss_end_date() {
        return ass_end_date;
    }

    public void setAss_end_date(String ass_end_date) {
        this.ass_end_date = ass_end_date;
    }
}
