package com.example.checkerslab_edulearning.mainHome_pkg;

public class popularCoursesModel {


    String courseImage,courseDiscount,courseName,subscriptionId;

    public popularCoursesModel(String courseImage, String courseDiscount, String courseName) {
        this.courseImage = courseImage;
        this.courseDiscount = courseDiscount;
        this.courseName = courseName;
    }

    public popularCoursesModel(String courseImage, String subscriptionId) {
        this.courseImage = courseImage;
        this.subscriptionId = subscriptionId;
    }

    public popularCoursesModel() {
    }

    public String getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(String courseImage) {
        this.courseImage = courseImage;
    }

    public String getCourseDiscount() {
        return courseDiscount;
    }

    public void setCourseDiscount(String courseDiscount) {
        this.courseDiscount = courseDiscount;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSubscriptionId() {
        return subscriptionId;
    }

    public void setSubscriptionId(String subscriptionId) {
        this.subscriptionId = subscriptionId;
    }
}
