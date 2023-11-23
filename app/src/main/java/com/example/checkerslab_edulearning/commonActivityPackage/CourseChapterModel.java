package com.example.checkerslab_edulearning.commonActivityPackage;

public class CourseChapterModel {

    String chapter_id,chapter_code,chapter_name,subject_id,total_topics,created_by,
            creation_date,last_updation_date,last_update_by,status;

    public CourseChapterModel(String chapter_id, String chapter_code, String chapter_name, String subject_id, String total_topics, String created_by, String creation_date, String last_updation_date, String last_update_by, String status) {
        this.chapter_id = chapter_id;
        this.chapter_code = chapter_code;
        this.chapter_name = chapter_name;
        this.subject_id = subject_id;
        this.total_topics = total_topics;
        this.created_by = created_by;
        this.creation_date = creation_date;
        this.last_updation_date = last_updation_date;
        this.last_update_by = last_update_by;
        this.status = status;
    }

    public CourseChapterModel() {
    }

    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getChapter_code() {
        return chapter_code;
    }

    public void setChapter_code(String chapter_code) {
        this.chapter_code = chapter_code;
    }

    public String getChapter_name() {
        return chapter_name;
    }

    public void setChapter_name(String chapter_name) {
        this.chapter_name = chapter_name;
    }

    public String getSubject_id() {
        return subject_id;
    }

    public void setSubject_id(String subject_id) {
        this.subject_id = subject_id;
    }

    public String getTotal_topics() {
        return total_topics;
    }

    public void setTotal_topics(String total_topics) {
        this.total_topics = total_topics;
    }

    public String getCreated_by() {
        return created_by;
    }

    public void setCreated_by(String created_by) {
        this.created_by = created_by;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public String getLast_updation_date() {
        return last_updation_date;
    }

    public void setLast_updation_date(String last_updation_date) {
        this.last_updation_date = last_updation_date;
    }

    public String getLast_update_by() {
        return last_update_by;
    }

    public void setLast_update_by(String last_update_by) {
        this.last_update_by = last_update_by;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
