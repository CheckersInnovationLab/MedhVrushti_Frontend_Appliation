package com.example.checkerslab_edulearning.NavigationDrawerPkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.R;
import com.mikhaellopez.circularprogressbar.CircularProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

public class AssessmentResult extends AppCompatActivity {


    TextView overView;
    TextView currentScore1,currentScore2,totalScore;
    CircularProgressBar circularProgressBar;
    static int obtainedMarks=0,totalMarks=0;
    static String userAssessmentID="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_result);


        Intent intent=getIntent();
        userAssessmentID=intent.getStringExtra("User_assessment_id");
        circularProgressBar=findViewById(R.id.assessment_result_progress_bar_id);
        currentScore1=findViewById(R.id.assessment_result_current_score_id1);
        currentScore2=findViewById(R.id.assessment_result_current_score_id2);
        totalScore=findViewById(R.id.assessment_result_total_score_id2);
        overView=findViewById(R.id.assessment_result_Overview_id);

        getAssessmentResult();



        overView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(AssessmentResult.this, AssessmentOverview.class);
                startActivity(intent1);
            }
        });

    }

    private void getAssessmentResult() {

        String url="http://apis-medhvrushti.checkerslab.com/api/v1/cil/user_assessments/get?user_ass_id="+userAssessmentID;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Retrieve the token from the JSON response
                            String obtained_marks = response.getString("obtained_marks");
                            String total_marks = response.getString("total_marks");

                            obtainedMarks=Integer.valueOf(obtained_marks);
                            totalMarks=Integer.valueOf(total_marks);

                            currentScore1.setText(obtained_marks);
                           currentScore2.setText(obtained_marks);
                            totalScore.setText(total_marks);
                            circularProgressBar.setProgressMax(totalMarks);
                            circularProgressBar.setProgress(Integer.valueOf(obtainedMarks));



                        } catch (JSONException e) {
                            e.printStackTrace();
                            // Handle the JSON Exception here
                        }
                    }
                },new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                        }
                    }
                }
                ) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
        };
        requestQueue.add(jsonObjectRequest);

    }
}