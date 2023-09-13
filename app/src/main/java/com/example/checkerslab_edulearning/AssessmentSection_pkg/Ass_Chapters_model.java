package com.example.checkerslab_edulearning.AssessmentSection_pkg;

public class Ass_Chapters_model {
    int chapterId,totalTopics;
    String chapterCode,chapterName;

    public Ass_Chapters_model(int chapterId, int totalTopics, String chapterCode, String chapterName) {
        this.chapterId = chapterId;
        this.totalTopics = totalTopics;
        this.chapterCode = chapterCode;
        this.chapterName = chapterName;
    }

    public Ass_Chapters_model() {
    }

    public int getChapterId() {
        return chapterId;
    }

    public void setChapterId(int chapterId) {
        this.chapterId = chapterId;
    }

    public int getTotalTopics() {
        return totalTopics;
    }

    public void setTotalTopics(int totalTopics) {
        this.totalTopics = totalTopics;
    }

    public String getChapterCode() {
        return chapterCode;
    }

    public void setChapterCode(String chapterCode) {
        this.chapterCode = chapterCode;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }
}
