package com.example.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.Theory_assessment_Activity;

public class Assessment_Instruction_Activity extends AppCompatActivity {

    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_instruction);

        startButton=findViewById(R.id.start_assessment_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), Theory_assessment_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });
    }
}