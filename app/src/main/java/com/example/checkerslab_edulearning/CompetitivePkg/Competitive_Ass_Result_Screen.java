package com.example.checkerslab_edulearning.CompetitivePkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.daimajia.numberprogressbar.OnProgressBarListener;
import com.example.checkerslab_edulearning.commonActivityPackage.Assessment_Solution_Screen;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;

import java.util.Timer;
import java.util.TimerTask;

public class Competitive_Ass_Result_Screen extends AppCompatActivity implements OnProgressBarListener {

    private Timer timer;

    private NumberProgressBar bnp;
    private TextView currentScore1;
    RelativeLayout solutionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competitive_ass_result_screen);

        solutionButton=findViewById(R.id.Ass_solutionButton_id);

        ///////////////vertical progress bar//////////

        bnp = (NumberProgressBar)findViewById(R.id.numberbar1);
        currentScore1=findViewById(R.id.Current_score_id1);
        bnp.setOnProgressBarListener(this);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        bnp.incrementProgressBy(1);
                    }
                });
            }
        }, 1000, 100);

    /////////////////////////////////////////////////////////

        CalculateResult();

        solutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Competitive_Ass_Result_Screen.this, Assessment_Solution_Screen.class);
                startActivity(intent);
            }
        });
    }

    private void CalculateResult() {
        int correctQuestion=0,unAttempt=0,wrong=0;
        for (int i=0;i< Test_Reminder_activity.testDataList.size();i++)
        {


            Log.d("Result","rear answer="+Test_Reminder_activity.testDataList.get(i).getAnswer()+" Selected answer is="+Test_Reminder_activity.testDataList.get(i).getSelectedAnswer());
            if (Test_Reminder_activity.testDataList.get(i).getSelectedAnswer()=="")
            {
                unAttempt++;
            }
            else
            {
                if (Test_Reminder_activity.testDataList.get(i).getSelectedAnswer().equals(Test_Reminder_activity.testDataList.get(i).getAnswer()))
                {
                    correctQuestion++;
                }
                else {
                    wrong++;
                }
            }
        }
        currentScore1.setText(String.valueOf(correctQuestion));
        Log.d("Result=","correct ="+correctQuestion+"\n wrong ="+wrong+"\n unattempt="+unAttempt);

    }


//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
    }
    @Override
    public void onProgressChange(int current, int max) {
        if(current == max) {
            Toast.makeText(getApplicationContext(), "ddddd", Toast.LENGTH_SHORT).show();
            bnp.setProgress(0);
        }
    }
}