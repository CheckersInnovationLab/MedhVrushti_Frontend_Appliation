package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkerslab_edulearning.R;

import java.util.Timer;
import java.util.TimerTask;

public class Assessment_Screen extends AppCompatActivity implements View.OnClickListener  {


    TextView ques,option1,option2,option3,option4,quesNo;
    TextView next,Submit;
    RelativeLayout optLayout1,optLayout2,optLayout3,optLayout4,nextLayout,skipLayout;
    int score=0;
    int totalQuestion ;
    int currentQuestionIndex = 0;
    String selectedAnswer = "";
    Selected_Test_Data_Model modelClass;

    //timer
    TextView timerTextView;
    CountDownTimer timer;
    private Timer quizetimer;
    private int totalTimeInMin=1;
    private int seconds=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_screen);


        ques=findViewById(R.id.Assessment_question_text_id);
        option1=findViewById(R.id.option_1_id);
        option2=findViewById(R.id.option_2_id);
        option3=findViewById(R.id.option_3_id);
        option4=findViewById(R.id.option_4_id);
        next=findViewById(R.id.nextQues_button_id);
        timerTextView=findViewById(R.id.Assessment_time_text_id);
        quesNo=findViewById(R.id.Assessment_number_text_id);
        Submit=findViewById(R.id.Assessment_submit_button_id);
        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubmitQuize();
            }
        });


        //layout
        optLayout1=findViewById(R.id.option1_layout);
        optLayout2=findViewById(R.id.option2_layout);
        optLayout3=findViewById(R.id.option3_layout);
        optLayout4=findViewById(R.id.option4_layout);
        nextLayout=findViewById(R.id.Assessment_next_layout);
        skipLayout=findViewById(R.id.Assessment_skip_layout);



        totalQuestion=Test_Reminder_activity.testDataList.size();

        optLayout1.setOnClickListener(this);
        optLayout2.setOnClickListener(this);
        optLayout3.setOnClickListener(this);
        optLayout4.setOnClickListener(this);

        skipLayout.setOnClickListener(this);



        loadNewQuestion();
    }

    private void  startTimer(TextView timertextView)
    {
        totalTimeInMin=1;
        seconds=0;
        quizetimer=new Timer();
        quizetimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (seconds==0)
                {
                    totalTimeInMin--;
                    seconds=59;
                }
                else {
                    seconds--;

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String finalMin=String.valueOf(totalTimeInMin);
                        String finalSec=String.valueOf(seconds);

                        if (finalMin.length()==1)
                        {
                            finalMin="0"+finalMin;
                        }
                        if (finalSec.length()==1)
                        {
                            finalSec="0"+finalSec;
                        }

                        if (seconds==0 && totalTimeInMin==0)
                        {
                            quizetimer.purge();
                            quizetimer.cancel();
                            Toast.makeText(Assessment_Screen.this, "time over", Toast.LENGTH_SHORT).show();
                            //  finish();
                        }
                        Toast.makeText(Assessment_Screen.this, seconds+"min"+totalTimeInMin, Toast.LENGTH_SHORT).show();
                        timertextView.setText(finalMin+":"+finalSec);
                    }
                });

            }
        },1000,1000);

    }


    @Override
    public void onClick(View view) {

        nextLayout.setOnClickListener(this);
        nextLayout.setBackgroundColor(Color.DKGRAY);
        optLayout1.setBackgroundColor(Color.WHITE);
        optLayout2.setBackgroundColor(Color.WHITE);
        optLayout3.setBackgroundColor(Color.WHITE);
        optLayout4.setBackgroundColor(Color.WHITE);

        // TextView clickedButton = (TextView) view;
        RelativeLayout clickedButton=(RelativeLayout) view;
        if(clickedButton.getId()==R.id.Assessment_next_layout){
            quizetimer.cancel();

            if(selectedAnswer.equals(modelClass.getAnswer())){
                score++;

                Toast.makeText(this, selectedAnswer+score, Toast.LENGTH_SHORT).show();
            }
            currentQuestionIndex++;
            loadNewQuestion();

        }
        else if (clickedButton.getId()==R.id.Assessment_skip_layout)
        {
            quizetimer.cancel();
            currentQuestionIndex++;
            loadNewQuestion();
        }
        else{
            //choices button clicked
            // selectedAnswer  = clickedButton.getText().toString();
            if (clickedButton.getId()==R.id.option1_layout){
                selectedAnswer  = option1.getText().toString();
//               optLayout1.setBackgroundColor(Color.GRAY);
            }
            if (clickedButton.getId()==R.id.option2_layout){
                selectedAnswer  = option2.getText().toString();
//               optLayout2.setBackgroundColor(Color.GRAY);
            }
            if (clickedButton.getId()==R.id.option3_layout){
                selectedAnswer  = option3.getText().toString();
//               optLayout3.setBackgroundColor(Color.GRAY);
            }
            if (clickedButton.getId()==R.id.option4_layout){
                selectedAnswer  = option4.getText().toString();
//               optLayout4.setBackgroundColor(Color.GRAY);
            }

            clickedButton.setBackgroundColor(Color.GRAY);

        }

    }

    void loadNewQuestion(){

        nextLayout.setOnClickListener(null);
        nextLayout.setBackgroundColor(Color.LTGRAY);

        startTimer(timerTextView);
        quesNo.setText(String.valueOf(currentQuestionIndex+1));
        if(currentQuestionIndex == totalQuestion ){
            SubmitQuize();
            return;
        }
        modelClass=Test_Reminder_activity.testDataList.get(currentQuestionIndex);

        ques.setText(modelClass.getQuestion());
        option1.setText(modelClass.getOption1());
        option2.setText(modelClass.getOption2());
        option3.setText(modelClass.getOption3());
        option4.setText(modelClass.getOption4());

    }

    void finishQuiz(){
        String passStatus = "";
        if(score > totalQuestion*0.60){
            passStatus = "Passed";
        }else{
            passStatus = "Failed";
        }
//
//        AlertDialog.Builder alert1=  new AlertDialog.Builder(this);
//                alert1.setTitle(passStatus);
//                alert1.setMessage("Score is "+ score+" out of "+ totalQuestion);
//                alert1.setPositiveButton("Restart",(dialogInterface, i) -> restartQuiz() );
//                alert1.setNegativeButton("Restart",(dialogInterface, i) -> alert1.setCancelable(true));
//                alert1.setCancelable(false);
//                alert1.show();


                ////////////////////
        AlertDialog.Builder builder = new AlertDialog.Builder(Assessment_Screen.this);
        builder.setMessage("Do you want to Submit the Test?");
        builder.setTitle("Submit Test");
        builder.setCancelable(false);
        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            finish();
            quizetimer.cancel();
            Intent intent=new Intent(Assessment_Screen.this,Assessment_Result_Screen.class);
            intent.putExtra("Score",String.valueOf(score));
            intent.putExtra("TotalQuestion",String.valueOf(Test_Reminder_activity.testDataList.size()));
            startActivity(intent);
        });
        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ///////////////////////



    }

    private void SubmitQuize() {
        finishQuiz();

    }

    void restartQuiz(){
        score = 0;
        quizetimer.cancel();
        currentQuestionIndex =0;
        totalTimeInMin=1;
        seconds=0;
        selectedAnswer="";
        loadNewQuestion();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        quizetimer.cancel();

    }
}