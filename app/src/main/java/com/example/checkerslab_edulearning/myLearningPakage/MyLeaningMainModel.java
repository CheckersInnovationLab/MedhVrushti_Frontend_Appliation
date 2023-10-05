package com.example.checkerslab_edulearning.myLearningPakage;

public class MyLeaningMainModel {


    String courseImgUrl,courseName,coursePrice;

    public MyLeaningMainModel() {
    }

    public MyLeaningMainModel(String courseImgUrl, String courseName, String coursePrice) {
        this.courseImgUrl = courseImgUrl;
        this.courseName = courseName;
        this.coursePrice = coursePrice;
    }

    public String getCourseImgUrl() {
        return courseImgUrl;
    }

    public void setCourseImgUrl(String courseImgUrl) {
        this.courseImgUrl = courseImgUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }
}
