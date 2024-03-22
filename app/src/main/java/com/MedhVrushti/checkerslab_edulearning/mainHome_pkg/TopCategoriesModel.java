package com.MedhVrushti.checkerslab_edulearning.mainHome_pkg;

public class TopCategoriesModel {

    String catName,catImgUrl;


    public TopCategoriesModel(String catName, String catImgUrl) {
        this.catName = catName;
        this.catImgUrl = catImgUrl;
    }

    public TopCategoriesModel() {
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getCatImgUrl() {
        return catImgUrl;
    }

    public void setCatImgUrl(String catImgUrl) {
        this.catImgUrl = catImgUrl;
    }
}
