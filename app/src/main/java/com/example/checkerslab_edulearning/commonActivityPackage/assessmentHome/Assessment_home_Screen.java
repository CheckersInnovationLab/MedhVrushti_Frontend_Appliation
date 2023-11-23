package com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.checkerslab_edulearning.R;

public class Assessment_home_Screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_home_screen);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout,new Final_Assessment_Tab()).commit();
    }


    public void onClick(View v){
        switch (v.getId()){
            case R.id.final_assessment_button_id:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout,
                                new Final_Assessment_Tab()).commit();
                break;
            case R.id.Unite_assessment_button_id:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout,
                                new Unite_Assessment_Tab()).commit();
                break;
            case R.id.chapter_assessment_button_id:
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout,
                                new ChapterWise_Assessment_Tab()).commit();
                break;

        }
    }
}