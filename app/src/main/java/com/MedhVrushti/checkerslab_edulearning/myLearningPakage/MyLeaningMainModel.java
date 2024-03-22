package com.MedhVrushti.checkerslab_edulearning.myLearningPakage;

public class MyLeaningMainModel {


    String user_subscription_id,user_id,subscription_id,standard_id,subscription_name,subscription_type,subscription_category,description,subscription_image,subscription_date,access_end_date,attribute1;

    public MyLeaningMainModel(String user_subscription_id, String user_id, String subscription_id, String standard_id, String subscription_name, String subscription_type, String subscription_category, String description, String subscription_image, String subscription_date, String access_end_date, String attribute1) {
        this.user_subscription_id = user_subscription_id;
        this.user_id = user_id;
        this.subscription_id = subscription_id;
        this.standard_id = standard_id;
        this.subscription_name = subscription_name;
        this.subscription_type = subscription_type;
        this.subscription_category = subscription_category;
        this.description = description;
        this.subscription_image = subscription_image;
        this.subscription_date = subscription_date;
        this.access_end_date = access_end_date;
        this.attribute1 = attribute1;
    }

    public MyLeaningMainModel() {
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

    public String getSubscription_name() {
        return subscription_name;
    }

    public void setSubscription_name(String subscription_name) {
        this.subscription_name = subscription_name;
    }

    public String getSubscription_type() {
        return subscription_type;
    }

    public void setSubscription_type(String subscription_type) {
        this.subscription_type = subscription_type;
    }

    public String getSubscription_category() {
        return subscription_category;
    }

    public void setSubscription_category(String subscription_category) {
        this.subscription_category = subscription_category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubscription_image() {
        return subscription_image;
    }

    public void setSubscription_image(String subscription_image) {
        this.subscription_image = subscription_image;
    }

    public String getSubscription_date() {
        return subscription_date;
    }

    public void setSubscription_date(String subscription_date) {
        this.subscription_date = subscription_date;
    }

    public String getAccess_end_date() {
        return access_end_date;
    }

    public void setAccess_end_date(String access_end_date) {
        this.access_end_date = access_end_date;
    }

    public String getAttribute1() {
        return attribute1;
    }

    public void setAttribute1(String attribute1) {
        this.attribute1 = attribute1;
    }
}

