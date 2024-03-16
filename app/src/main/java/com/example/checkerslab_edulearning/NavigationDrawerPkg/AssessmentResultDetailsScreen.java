package com.example.checkerslab_edulearning.NavigationDrawerPkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.CompetitivePkg.Competitive_Ass_Result_Screen;
import com.example.checkerslab_edulearning.ErrorStatusDialog;
import com.example.checkerslab_edulearning.LoadingDialog;
import com.example.checkerslab_edulearning.ProfilePackage.EducationalDetailsActivity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.commonActivityPackage.Assessment_Solution_Screen;
import com.example.checkerslab_edulearning.commonActivityPackage.RankingLeaderBoard;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AssessmentResultDetailsScreen extends AppCompatActivity {


    private TextView assessmentName,obtainedMarks,totalMarks,userRanking,totalParticipants,correctQuesCount,wrongQuesCount,unAnsweredQuesCount;
    private String userAssessmentId,assessmentNameS;
    public static String assessmentId="";
    private ImageView backButton;
    private RelativeLayout viewSolutionButton,viewRankingButton;
    private LoadingDialog loadingDialog;
    private ErrorStatusDialog errorStatusDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_result_details_screen);
        
        Intent intent=getIntent();
//        userAssessmentId=intent.getStringExtra("User_assessment_id");
        assessmentNameS=intent.getStringExtra("User_assessment_name");
        assessmentId=intent.getStringExtra("assessment_id");

        loadingDialog=new LoadingDialog(AssessmentResultDetailsScreen.this);
        errorStatusDialog=new ErrorStatusDialog(AssessmentResultDetailsScreen.this);


       assessmentName=findViewById(R.id.AssessmentResultDetails_AssessmentName_id);
        obtainedMarks=findViewById(R.id.AssessmentResultDetails_ObtainedMarks_id);
        totalMarks=findViewById(R.id.AssessmentResultDetails_OutOfMarks_id);
        userRanking=findViewById(R.id.AssessmentResultDetails_Ranking_id);
        totalParticipants=findViewById(R.id.AssessmentResultDetails_Ranking_OutOf_id);
        correctQuesCount=findViewById(R.id.AssessmentResultDetails_CorrectQuestionCount_id);
        wrongQuesCount=findViewById(R.id.AssessmentResultDetails_WrongQuestionCount_id);
        unAnsweredQuesCount=findViewById(R.id.AssessmentResultDetails_UnAnsweredCountCount_id);
        backButton=findViewById(R.id.assessmentResultDetails_Toolbar_back_button);
        viewSolutionButton=findViewById(R.id.Result_details_ViewSolutionButton_id);
        viewRankingButton=findViewById(R.id.Result_details_ViewRankingButton_id);
        viewRankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(getApplicationContext(), RankingLeaderBoard.class);
                intent1.putExtra("assessmentId",AssessmentResultDetailsScreen.assessmentId);
                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent1);
            }
        });

        viewSolutionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AssessmentResultDetailsScreen.this, Assessment_Solution_Screen.class);
                intent.putExtra("assessment_id",assessmentId);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        
        getAssessmentDetails(assessmentId);
        assessmentName.setText(assessmentNameS);

    }

    private void getAssessmentDetails(String assessmentId) {
       loadingDialog.startLoadingDialog();

        String url= StaticFile.Url+"/api/v1/cil/user_assessments/get/by/user_id_and_assessment_id?user_id="+StaticFile.userId+"&assessment_id="+assessmentId;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

       JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                       loadingDialog.dismissDialog();
                            try {

                             String obtainedMarksL=response.getString("obtained_marks");
                             String totalMarksL=response.getString("total_marks");
                             String correctAnswerCountL=response.getString("attribute10");
                             String wrongAnswerCountL=response.getString("attribute8");
                             String unAnsweredQuesCountL=response.getString("attribute9");
                             String assessmentIdL=response.getString("assessment_id");
                             getRankingDetails(StaticFile.userId,assessmentIdL);

                             setAssessmentDetails(obtainedMarksL,totalMarksL,correctAnswerCountL,wrongAnswerCountL,unAnsweredQuesCountL);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            loadingDialog.dismissDialog();
                            errorStatusDialog.showErrorMessage();
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                            Log.d("getAssessmentDetails",errorMessage);
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

    private void setAssessmentDetails(String obtainedMarksL, String totalMarksL, String correctAnswerCountL, String wrongAnswerCountL, String unAnsweredQuesCountL) {

     obtainedMarks.setText(obtainedMarksL);
     totalMarks.setText("Out Of "+totalMarksL);
     correctQuesCount.setText(correctAnswerCountL);
     wrongQuesCount.setText(wrongAnswerCountL);
     unAnsweredQuesCount.setText(unAnsweredQuesCountL);
    }
    private void getRankingDetails(String userId, String assessmentIdL) {

        String url="http://89.116.33.21:5000/get/CET/Ranking";

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", userId);
            requestData.put("assessment_id", assessmentIdL);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String userRankL = response.getString("rank");
                            String totalParticipantsL=response.getString("total_participants");

                            userRanking.setText(userRankL);
                            totalParticipants.setText("Out Of "+totalParticipantsL);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        if (error.networkResponse != null) {
                            errorStatusDialog.showErrorMessage();
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
                        }
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}