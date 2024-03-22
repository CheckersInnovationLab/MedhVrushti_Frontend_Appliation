package com.MedhVrushti.checkerslab_edulearning.storePackage;

public class StoreCoursesModel {

    String subscription_id,subscription_code,subscription_name,subscription_type,subscription_category,standard_id,subject_id,subscription_price,description,default_discount,subscription_img_url,total_validity;


    public StoreCoursesModel(String subscription_id, String subscription_code, String subscription_name, String subscription_type, String subscription_category, String standard_id, String subject_id, String subscription_price, String description, String default_discount, String subscription_img_url, String total_validity) {
        this.subscription_id = subscription_id;
        this.subscription_code = subscription_code;
        this.subscription_name = subscription_name;
        this.subscription_type = subscription_type;
        this.subscription_category = subscription_category;
        this.standard_id = standard_id;
        this.subject_id = subject_id;
        this.subscription_price = subscription_price;
        this.description = description;
        this.default_discount = default_discount;
        this.subscription_img_url = subscription_img_url;
        this.total_validity = total_validity;
    }

    public StoreCoursesModel() {
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getSubscription_code() {
        return subscription_code;
    }

    public void setSubscription_code(String subscription_code) {
        this.subscription_code = subscription_code;
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

    public String getSubscription_price() {
        return subscription_price;
    }

    public void setSubscription_price(String subscription_price) {
        this.subscription_price = subscription_price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefault_discount() {
        return default_discount;
    }

    public void setDefault_discount(String default_discount) {
        this.default_discount = default_discount;
    }

    public String getSubscription_img_url() {
        return subscription_img_url;
    }

    public void setSubscription_img_url(String subscription_img_url) {
        this.subscription_img_url = subscription_img_url;
    }

    public String getTotal_validity() {
        return total_validity;
    }

    public void setTotal_validity(String total_validity) {
        this.total_validity = total_validity;
    }
}
