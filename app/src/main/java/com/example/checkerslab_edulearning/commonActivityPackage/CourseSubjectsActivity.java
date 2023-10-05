package com.example.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainAdapter;

import java.util.ArrayList;

public class CourseSubjectsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<CourseSubjectModel> subjectModelArrayList;
    LinearLayoutManager VerticalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_subjects);

       recyclerView=findViewById(R.id.Courses_subject_recycler_id);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        subjectModelArrayList=new ArrayList<>();
        AddItemsToTopCatRecyclerView();
    }

    private void AddItemsToTopCatRecyclerView() {


        subjectModelArrayList.add(new CourseSubjectModel("Science","12"));
        subjectModelArrayList.add(new CourseSubjectModel("Science","13"));
        subjectModelArrayList.add(new CourseSubjectModel("Science","9"));
        CourseSubjectAdapter courseSubjectAdapter=new CourseSubjectAdapter(subjectModelArrayList,getApplicationContext());
        recyclerView.setAdapter(courseSubjectAdapter);
    }
}