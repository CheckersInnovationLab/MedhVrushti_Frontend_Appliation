package com.MedhVrushti.checkerslab_edulearning.commonActivityPackage;

public class RankingModel {
    String userName,userId,userRanking,userObtainedMarks;

    public RankingModel(String userName, String userId, String userRanking, String userObtainedMarks) {
        this.userName = userName;
        this.userId = userId;
        this.userRanking = userRanking;
        this.userObtainedMarks = userObtainedMarks;
    }

    public RankingModel() {
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserRanking() {
        return userRanking;
    }

    public void setUserRanking(String userRanking) {
        this.userRanking = userRanking;
    }

    public String getUserObtainedMarks() {
        return userObtainedMarks;
    }

    public void setUserObtainedMarks(String userObtainedMarks) {
        this.userObtainedMarks = userObtainedMarks;
    }
}
