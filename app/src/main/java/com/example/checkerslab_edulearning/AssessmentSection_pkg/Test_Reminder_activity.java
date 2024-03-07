package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.CompetitivePkg.CompetetiveAssessmentScreen;
import com.example.checkerslab_edulearning.CompetitivePkg.Competitive_Assessment_Main_Screen2;
import com.example.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentModel;
import com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome.Assessment_home_Screen;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test_Reminder_activity extends AppCompatActivity {

    Button StartButton;

    public static ArrayList<Selected_Test_Data_Model> testDataList;
    String topicId;
    public static final int Not_Visited=0;
    public static final int UnAnswered=1;
    public static final int Answered=2;
    public static final int Review=3;
    private ProgressBar questionPaperLoadingBar;
    public static String assessmentID="",AssessmentNameS="",assessmentTypeS="",assessmentCategoryS="",totalMarksS="",totalTimeS="",assessmentLanguageS=""
                  ,assessmentDescS="",complexityLevelS="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_reminder);

        Intent intent=getIntent();
        assessmentID=intent.getStringExtra("assessment_id");

        getAssessmentDetails();


        testDataList=new ArrayList<>();
        questionPaperLoadingBar=findViewById(R.id.QuestionPaperGeneratorLoading_id);
        questionPaperLoadingBar.setVisibility(View.VISIBLE);

      //  generateQuestionPaper(assessmentID);

        StartButton=findViewById(R.id.are_you_ready_button);
        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent=new Intent(Test_Reminder_activity.this, CompetetiveAssessmentScreen.class);
//                startActivity(intent);
                 addUserAssessmentData(assessmentID);
            }
        });

    }

    private void addUserAssessmentData(String assessmentIDL) {
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
                        // Handle error response
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Log.d("userSubscription :",errorMessage);
                            System.out.println("Error Status Code: " + statusCode);
                            System.out.println("Error Response Data: " + errorMessage);
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

    private void generateQuestionPaper(String assessmentId) {

        String Url="http://89.116.33.21:5000/generate/CET/questionPaper";
        Log.d("assessmentIDD",assessmentId);

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("assessment_id", assessmentId);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("QuestionPaperGenerator","Success");

                        try {
                            JSONArray jsonArray = response.getJSONArray("question_paper");

                            Log.d("assessmentIDD",String.valueOf(jsonArray.length()));
                            for (int n = 0; n < 1; n++) {
                                JSONObject typeObject = jsonArray.getJSONObject(n);

                                JSONArray questionsArray=typeObject.getJSONArray("questions");

                                for (int j=0;j<questionsArray.length();j++) {
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
                                Log.d("assessmentIDD",String.valueOf(testDataList.size()));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            questionPaperLoadingBar.setVisibility(View.GONE);
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                         Log.d("QuestionPaperGenerator",errorMessage);
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

        //////////////////////////////////////////Tested Code for latex question///////////////////////////////////////////

//        String Url="http://89.116.33.21:5000/generate/CET/questionPaper";
//        //String Url="https://medhvrushti.checkerslab.com/api/v1/cil/cet_questions/get/all/by/subject_and_chapter_and_topic?subject_id=1001&chapter_id=105&topic_id=100128";
//        Log.d("assessmentIDD",assessmentId);
//
//        JSONObject requestData = new JSONObject();
//        try {
//            requestData.put("assessment_id", assessmentId);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Url, requestData,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Log.d("QuestionPaperGenerator","Success");
//
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("question_paper");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject typeObject = jsonArray.getJSONObject(i);
//
//                                JSONArray questionsArray=typeObject.getJSONArray("questions");
//
//                                for (int j=0;j<questionsArray.length();j++) {
//                                    JSONObject question = questionsArray.getJSONObject(j);
//                                    Selected_Test_Data_Model model = new Selected_Test_Data_Model(question.getInt("question_id"),
//                                            question.getInt("marks"),
//                                            question.getString("question_type"),
//                                            question.getString("ques_line_by_latex"),
//                                            question.getString("option1_latex"),
//                                            question.getString("option2_latex"),
//                                            question.getString("option3_latex"),
//                                            question.getString("option4_latex"),
//                                            question.getString("answer_latex"),
//                                            question.getString("desc_line_by_latex"),
//                                            "Not_Visited",
//                                            ""
//                                    );
//
//                                    testDataList.add(model);
//
//                                    questionPaperLoadingBar.setVisibility(View.GONE);
//                                    StartButton.setVisibility(View.VISIBLE);
//                                }
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle error response
//                        if (error.networkResponse != null) {
//                            questionPaperLoadingBar.setVisibility(View.GONE);
//                            int statusCode = error.networkResponse.statusCode;
//                            byte[] errorResponseData = error.networkResponse.data; // Error response data
//                            String errorMessage = new String(errorResponseData); // Convert error data to string
//                            // Print the error details
//                         Log.d("QuestionPaperGenerator",errorMessage);
//                        }
//                    }
//                }
//        ) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//        };
//        requestQueue.add(jsonObjectRequest);

//        Log.d("status","success1");
//        String Url="https://medhvrushti.checkerslab.com/api/v1/cil/cet_questions/get/all/by/subject_and_chapter_and_topic?subject_id=1005&chapter_id=117&topic_id=100222";
//        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//
//        testDataList.clear();
//        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, Url,null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//
//                        Log.d("status","success2");
//
//                        // Handle success response from the server
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject object = response.getJSONObject(i);
//                                Selected_Test_Data_Model model = new Selected_Test_Data_Model(object.getInt("question_id"),
//                                            object.getInt("marks"),
//                                            object.getString("question_type"),
//                                            object.getString("ques_line_by_latex"),
//                                            object.getString("option1_latex"),
//                                            object.getString("option2_latex"),
//                                            object.getString("option3_latex"),
//                                            object.getString("option4_latex"),
//                                            object.getString("answer_latex"),
//                                            object.getString("desc_line_by_latex"),
//                                            "Not_Visited",
//                                            "",
//                                            "No",
//                                        object.getString("question_diagrams_url"),
//                                        object.getString("description_diagrams_url")
//                                );
//
//                                     testDataList.add(model);
//                                    questionPaperLoadingBar.setVisibility(View.GONE);
//                                    StartButton.setVisibility(View.VISIBLE);
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                        Log.d("status",String.valueOf(testDataList.size()));
//
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Handle error response
//                        if (error.networkResponse != null) {
//                            int statusCode = error.networkResponse.statusCode;
//                            byte[] errorResponseData = error.networkResponse.data; // Error response data
//                            String errorMessage = new String(errorResponseData); // Convert error data to string
//                            // Print the error details
//                            Log.d("status","Failed");
//                        }
//                    }
//                }
//        ) {
//            @Override
//            public String getBodyContentType() {
//                return "application/json; charset=utf-8";
//            }
//        };
//        requestQueue.add(jsonObjectRequest);


    }
    private void getAssessmentDetails() {

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
                            totalTimeS  = response.getString("total_time");
                            assessmentLanguageS  = response.getString("assessment_language");
                            assessmentDescS  = response.getString("assessment_desc");
                           complexityLevelS  = response.getString("complexity_level");
//                            String parentMobNumber  = response.getString("parents_contact_no");

                            Log.d("AssessmentName",AssessmentNameS);
                            generateQuestionPaper(assessmentID);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                },
                new Response.ErrorListener() {
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

}