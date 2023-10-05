package com.example.checkerslab_edulearning.storePackage;

public class StoreCoursesModel {

    String courseImgUrl,courseName,coursePrice;

    public StoreCoursesModel(String courseImgUrl, String courseName, String coursePrice) {
        this.courseImgUrl = courseImgUrl;
        this.courseName = courseName;
        this.coursePrice = coursePrice;
    }

    public StoreCoursesModel() {
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
