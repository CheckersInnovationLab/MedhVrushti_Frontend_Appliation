package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.CompetitivePkg.CompetetiveAssessmentScreen;
import com.example.checkerslab_edulearning.ErrorStatusDialog;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Test_Reminder_activity extends AppCompatActivity {

    private  Button StartButton;
    private ImageView backButton;
    private TextView assessmentName,assessmentMarks,assessmentTime;
    public static ArrayList<Selected_Test_Data_Model> testDataList;
    private ProgressBar questionPaperLoadingBar;
    public static String assessmentID="",AssessmentNameS="",assessmentTypeS="",assessmentCategoryS="",totalMarksS="",assessmentLanguageS=""
                  ,assessmentDescS="",complexityLevelS="",assessmentSetId="";
    public static int totalTimeS=0;
    private ErrorStatusDialog errorStatusDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_reminder);

        Intent intent=getIntent();
        assessmentID=intent.getStringExtra("assessment_id");
        errorStatusDialog=new ErrorStatusDialog(Test_Reminder_activity.this);



        assessmentName=findViewById(R.id.Test_name_oo1);
        assessmentMarks=findViewById(R.id.Assessment_total_marks);
        assessmentTime=findViewById(R.id.Assessment_total_time);
        backButton=findViewById(R.id.TestReminder_BackButton_id);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        questionPaperLoadingBar=findViewById(R.id.QuestionPaperGeneratorLoading_id);

        getAssessmentDetails();
        testDataList=new ArrayList<>();


        StartButton=findViewById(R.id.are_you_ready_button);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 addUserAssessmentData(assessmentSetId,assessmentID);
            }
        });

    }

    private void addUserAssessmentData(String assessmentSetID, String assessmentIDL) {

         String userAssessmentUrl=StaticFile.Url+"/api/v1/cil/user_assessments/add";

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(Calendar.getInstance().getTime());

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id",StaticFile.userId);
            requestData.put("assessment_id",assessmentIDL);
            requestData.put("total_marks", totalMarksS);
//            requestData.put("total_time", "20");
            requestData.put("ass_start_date", date);
            requestData.put("result_status", "Pending");
            requestData.put("status", "Active");
            requestData.put("remarks", "Pending");
            requestData.put("created_by", "MedhVrushti App");
            requestData.put("last_updated_by", "MedhVrushti App");

            requestData.put("ass_set_id",assessmentSetID);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, userAssessmentUrl,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Handle success response from the server
                        try {
                            Intent intent=new Intent(Test_Reminder_activity.this, CompetetiveAssessmentScreen.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), "Assessment started", Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        errorStatusDialog.showErrorMessage();
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Toast.makeText(Test_Reminder_activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ){
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", getBodyContentType());
                headers.put("Authorization", "Bearer " +StaticFile.bearToken);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }

    private void getAssessmentDetails() {
        questionPaperLoadingBar.setVisibility(View.VISIBLE);

        String assDetailsId=StaticFile.Url+"/api/v1/cil/assessments/get?assessment_id="+assessmentID;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, assDetailsId,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
                        try {
                            AssessmentNameS  = response.getString("assessment_name");
                            assessmentTypeS  = response.getString("assessment_type");
                            assessmentCategoryS  = response.getString("assessment_category");
                            totalMarksS  = response.getString("total_marks");
                            totalTimeS  = response.getInt("total_time");
                            assessmentLanguageS  = response.getString("assessment_language");
                            assessmentDescS  = response.getString("assessment_desc");
                           complexityLevelS  = response.getString("complexity_level");

                            generateQuestionPaper(assessmentID);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        assessmentName.setText(AssessmentNameS);
                        assessmentMarks.setText(totalMarksS);
                        assessmentTime.setText(String.valueOf(totalTimeS));
                    }

                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        errorStatusDialog.showErrorMessage();
                        questionPaperLoadingBar.setVisibility(View.GONE);
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Toast.makeText(Test_Reminder_activity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        ){

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", getBodyContentType());
                headers.put("Authorization", "Bearer " + StaticFile.bearToken);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }
    private void generateQuestionPaper(String assessmentId) {
        String Url = "http://89.116.33.21:5000/assessment/question/paper/generator?assessment_id="+assessmentId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("QuestionPaperGenerator", "log2");

                        try {
                            JSONArray jsonArray = response.getJSONArray("question_paper");


                            Log.d("QuestionPaperGenerator", "log3");

                            for (int n = 0; n < 1; n++) {
                                Log.d("QuestionPaperGenerator", "log4");
                                JSONObject typeObject = jsonArray.getJSONObject(n);
                                assessmentSetId=typeObject.getString("ass_set_id");
                                JSONArray questionsArray = typeObject.getJSONArray("questions");

                                for (int j = 0; j < questionsArray.length(); j++) {
                                    Log.d("QuestionPaperGenerator", "log5");
                                    JSONObject question = questionsArray.getJSONObject(j);
                                    Selected_Test_Data_Model model = new Selected_Test_Data_Model(question.getInt("question_id"),
                                            question.getInt("marks"),
                                            question.getString("question_type"),
                                            question.getString("ques_line_by_latex"),
                                            question.getString("option1_latex"),
                                            question.getString("option2_latex"),
                                            question.getString("option3_latex"),
                                            question.getString("option4_latex"),
                                            question.getString("answer_latex"),
                                            question.getString("desc_line_by_latex"),
                                            "Not_Visited",
                                            "",
                                            question.getString("question_diagrams_url"),
                                            question.getString("description_diagrams_url"),
                                            question.getString("question_type_id"),
                                            false
                                    );

                                    testDataList.add(model);

                                }

                                questionPaperLoadingBar.setVisibility(View.GONE);
                                StartButton.setVisibility(View.VISIBLE);

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        questionPaperLoadingBar.setVisibility(View.GONE);
                        errorStatusDialog.showErrorMessage();
                        // Handle error response
                        if (error.networkResponse != null) {
                            questionPaperLoadingBar.setVisibility(View.GONE);
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Log.d("QuestionPaperGenerator", errorMessage);
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