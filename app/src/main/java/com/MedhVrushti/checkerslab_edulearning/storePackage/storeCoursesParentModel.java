package com.MedhVrushti.checkerslab_edulearning.storePackage;

import java.util.List;

public class storeCoursesParentModel {


    private String courseType;
    private List<StoreCoursesModel> coursesItemList;

    public storeCoursesParentModel(String courseType, List<StoreCoursesModel> coursesItemList) {
        this.courseType = courseType;
        this.coursesItemList = coursesItemList;
    }

    public storeCoursesParentModel() {
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public List<StoreCoursesModel> getCoursesItemList() {
        return coursesItemList;
    }

    public void setCoursesItemList(List<StoreCoursesModel> coursesItemList) {
        this.coursesItemList = coursesItemList;
    }
}
