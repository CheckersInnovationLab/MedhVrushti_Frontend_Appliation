package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.commonActivityPackage.Assessment_Solution_Screen;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

public class Assessment_Result_Screen extends AppCompatActivity {

    TextView solution,reTest;
    TextView currentScore1,currentScore2,totalQuestionNo,PredictionResult;
    CircularProgressBar circularProgressBar;
    String Score;
    int totalQuestion,scoreInt,averageScore;
    String url = "http://10.0.2.2:5000/predict";
    Button Predict;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_result_screen);



        Intent intent=getIntent();
        Score=intent.getStringExtra("Score");
        scoreInt=Integer.valueOf(Score);
        String totalQues=intent.getStringExtra("TotalQuestion");
        totalQuestion=Integer.valueOf(totalQues);
        averageScore=15;
        Toast.makeText(this,totalQues , Toast.LENGTH_SHORT).show();
        circularProgressBar=findViewById(R.id.circularProgressBar_id);

        currentScore1=findViewById(R.id.Current_score_id1);
        currentScore2=findViewById(R.id.Current_score_id2);
        totalQuestionNo=findViewById(R.id.total_question_id);
        solution=findViewById(R.id.Assessment_solution_button_id);
        reTest=findViewById(R.id.Assessment_ReTest_button_id);
        PredictionResult=findViewById(R.id.Prediction_result_text_id);
        Predict=findViewById(R.id.Predict_button_id);
        solution.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(Assessment_Result_Screen.this, Assessment_Solution_Screen.class);
                startActivity(intent1);
            }
        });
        reTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        currentScore1.setText(Score);
        currentScore2.setText(Score);
        totalQuestionNo.setText(totalQues);
        circularProgressBar.setProgressMax(totalQuestion);
        circularProgressBar.setProgress(Integer.valueOf(Score));

     //   startPrediction(scoreInt,averageScore);
        Predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent1=new Intent(Assessment_Result_Screen.this,ProgressPredictionScreen.class);
//                intent1.putExtra("CurrentScore",Score);
//                startActivity(intent1);

            }
        });
    }

//    private void startPrediction(int scoreInt, int averageScore) {
//        RequestQueue queue = Volley.newRequestQueue(AssessmentResultScreen.this);
//
//        StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String data = jsonObject.getString("output");
//                    if(data.equals("Poor"))
//                    {
//                        PredictionResult.setText("Lower Level." + "Yor Have 30% chances to reach your goal");
//                    }
//                    else if (data.equals("Satisfactory"))
//                    {
//                        PredictionResult.setText("Average Level"+"You have 50% chances to reach your goal ");
//                    }
//                    else if(data.equals("Best"))
//                    {
//                        PredictionResult.setText("Excellent Level"+"You have 80% chances to reach your goal ");
//                    }
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(AssessmentResultScreen.this, "error"+error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }) {
//            @Override
//            protected Map<String,String> getParams(){
//                Map<String,String> params = new HashMap<String,String>();
//                params.put("total","20");
//                params.put("obtained",Score);
//                params.put("avg","6");
//
//                return params;
//            }
//        };
//        queue.add(stringRequest);
//    }
}