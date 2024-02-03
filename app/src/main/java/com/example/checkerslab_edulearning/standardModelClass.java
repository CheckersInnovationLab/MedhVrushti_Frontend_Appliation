package com.example.checkerslab_edulearning;

public class standardModelClass {
    String Name,Id;

    public standardModelClass(String name, String id) {
        Name = name;
        Id = id;
    }
    @Override
    public String toString() {
        return Name; // Display only the name in the AutoCompleteTextView
    }

    public standardModelClass() {
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
