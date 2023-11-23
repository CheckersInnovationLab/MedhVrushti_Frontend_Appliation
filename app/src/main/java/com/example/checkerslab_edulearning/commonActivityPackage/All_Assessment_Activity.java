package com.example.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.checkerslab_edulearning.R;

import java.util.ArrayList;

public class All_Assessment_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<AllAssessmentModel> assessmentList;
    LinearLayoutManager VerticalLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_assessment);



        recyclerView=findViewById(R.id.all_assessment_recyclerView_id);
        assessmentList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);
        AddItemsToTopCatRecyclerView();
    }


    private void AddItemsToTopCatRecyclerView() {



//        assessmentList.add(new AllAssessmentModel("Chapter 1 Real Numbers","30"));
//        assessmentList.add(new AllAssessmentModel("Chapter 2 Polynomials","70"));
//        assessmentList.add(new AllAssessmentModel("Chapter 3 Pair of Linear Equations in Two Variables","70"));
//        assessmentList.add(new AllAssessmentModel("Chapter 4 Quadratic Equations","70"));
//        assessmentList.add(new AllAssessmentModel("Chapter 5 Arithmetic Progressions","70"));
//        AllAssessmentAdapter allAssessmentAdapter=new AllAssessmentAdapter(assessmentList,getApplicationContext());
//        recyclerView.setAdapter(allAssessmentAdapter);
    }
}