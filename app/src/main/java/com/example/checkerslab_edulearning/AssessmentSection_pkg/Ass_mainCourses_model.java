package com.example.checkerslab_edulearning.AssessmentSection_pkg;

public class Ass_mainCourses_model {


    int boardId;
    String  boardCode,boardName,boardDescription,boardImgUrl;

    public Ass_mainCourses_model() {
    }

    public Ass_mainCourses_model(int boardId, String boardCode, String boardName, String boardDescription, String boardImgUrl) {
        this.boardId = boardId;
        this.boardCode = boardCode;
        this.boardName = boardName;
        this.boardDescription = boardDescription;
        this.boardImgUrl = boardImgUrl;
    }

    public int getBoardId() {
        return boardId;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardCode() {
        return boardCode;
    }

    public void setBoardCode(String boardCode) {
        this.boardCode = boardCode;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }

    public String getBoardDescription() {
        return boardDescription;
    }

    public void setBoardDescription(String boardDescription) {
        this.boardDescription = boardDescription;
    }

    public String getBoardImgUrl() {
        return boardImgUrl;
    }

    public void setBoardImgUrl(String boardImgUrl) {
        this.boardImgUrl = boardImgUrl;
    }
}
