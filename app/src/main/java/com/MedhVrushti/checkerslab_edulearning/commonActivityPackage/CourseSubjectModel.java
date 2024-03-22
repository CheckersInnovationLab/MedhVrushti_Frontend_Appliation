package com.MedhVrushti.checkerslab_edulearning.commonActivityPackage;

public class CourseSubjectModel {



    String user_subscription_id,user_id,subscription_id,standard_id,subject_id,subject_name,attribute1;

    public CourseSubjectModel()
    {
    }

    public CourseSubjectModel(String user_subscription_id, String user_id, String subscription_id, String standard_id, String subject_id, String subject_name, String attribute1) {
        this.user_subscription_id = user_subscription_id;
        this.user_id = user_id;
        this.subscription_id = subscription_id;
        this.standard_id = standard_id;
        this.subject_id = subject_id;
        this.subject_name = subject_name;
        this.attribute1 = attribute1;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }

    public String getUser_subscription_id() {
        return user_subscription_id;
    }

    public void setUser_subscription_id(String user_subscription_id) {
        this.user_subscription_id = user_subscription_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getStandard_id() {
        return standard_id;
    }

    public void setStandard_id(String standard_id) {
        this.standard_id = standard_id;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }


}
