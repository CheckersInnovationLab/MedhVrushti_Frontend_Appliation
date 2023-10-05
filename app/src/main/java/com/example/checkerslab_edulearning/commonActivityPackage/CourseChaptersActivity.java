package com.example.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainAdapter;

import java.util.ArrayList;

public class CourseChaptersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CourseChapterModel> courseChapterList;
    LinearLayoutManager VerticalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_chapters);

        recyclerView=findViewById(R.id.Course_chapter_recyclerView_id);
        courseChapterList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);
        AddItemsToTopCatRecyclerView();
    }


    private void AddItemsToTopCatRecyclerView() {



        courseChapterList.add(new CourseChapterModel("Chapter 1 Real Numbers"));
        courseChapterList.add(new CourseChapterModel("Chapter 2 Polynomials"));
        courseChapterList.add(new CourseChapterModel("Chapter 3 Pair of Linear Equations in Two Variables"));
        courseChapterList.add(new CourseChapterModel("Chapter 4 Quadratic Equations"));
        courseChapterList.add(new CourseChapterModel("Chapter 5 Arithmetic Progressions"));
        CourseChapterAdapter courseChapterAdapter=new CourseChapterAdapter(courseChapterList,getApplicationContext());
        recyclerView.setAdapter(courseChapterAdapter);
    }

}