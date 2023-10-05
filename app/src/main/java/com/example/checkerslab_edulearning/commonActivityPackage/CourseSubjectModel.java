package com.example.checkerslab_edulearning.commonActivityPackage;

public class CourseSubjectModel {


    String subjectName,totalChapter;

    public CourseSubjectModel() {
    }

    public CourseSubjectModel(String subjectName, String totalChapter) {
        this.subjectName = subjectName;
        this.totalChapter = totalChapter;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getTotalChapter() {
        return totalChapter;
    }

    public void setTotalChapter(String totalChapter) {
        this.totalChapter = totalChapter;
    }
}
