package com.example.checkerslab_edulearning.AssessmentSection_pkg;

public class Ass_Topic_model {
    int topicId,totalQuestion;
    String topicCode,topicName;

    public Ass_Topic_model(int topicId, int totalQuestion, String topicCode, String topicName) {
        this.topicId = topicId;
        this.totalQuestion = totalQuestion;
        this.topicCode = topicCode;
        this.topicName = topicName;
    }

    public int getTopicId() {
        return topicId;
    }

    public void setTopicId(int topicId) {
        this.topicId = topicId;
    }

    public int getTotalQuestion() {
        return totalQuestion;
    }

    public void setTotalQuestion(int totalQuestion) {
        this.totalQuestion = totalQuestion;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}
