package com.example.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.checkerslab_edulearning.AssessmentSection_pkg.Selected_Test_Data_Model;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;

public class Assessment_Solution_Screen extends AppCompatActivity {

    RecyclerView recyclerView;
    Selected_Test_Data_Model modelClass;
    TextView toolbarTitle;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_solution_screen);

        Toolbar toolbar=findViewById(R.id.assessment_solution_toolbar_id);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.assessment_solution_Toolbar_title);
        back=findViewById(R.id.assessment_solution_Toolbar_back_button);
        toolbarTitle.setText("Assessment Solution");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.Assessment_solution_recyclerView);

        Assessment_solution_adapter myAdapter = new Assessment_solution_adapter(Test_Reminder_activity.testDataList, getApplicationContext());
//               // GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
//                recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}