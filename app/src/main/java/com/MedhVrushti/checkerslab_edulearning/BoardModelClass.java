package com.MedhVrushti.checkerslab_edulearning;

public class BoardModelClass {

    String Name,Id;

    public BoardModelClass(String name, String id) {
        Name = name;
        Id = id;
    }
    @Override
    public String toString() {
        return Name; // Display only the name in the AutoCompleteTextView
    }
    public BoardModelClass() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
