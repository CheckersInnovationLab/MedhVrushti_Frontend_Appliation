package com.example.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentAdapter;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentModel;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.Theory_assessment_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Assessment_Instruction_Activity extends AppCompatActivity {

    private Button startButton;
     public static String assessmentID="",assessmentMarks="",assessmentName="";
    private String url="http://apis-medhvrushti.checkerslab.com/api/v1/cil/user_assessments/add";
    RecyclerView recyclerView;
    public  static ArrayList<ParentModel> mainQuestionList;
    public  static ArrayList<ChildModel> subQuestionList;
    ParentAdapter parentAdapter;
    private ProgressBar loadingPB;
    boolean isProgressVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_instruction);


        Intent intent=getIntent();
        assessmentID=intent.getStringExtra("assessment_id");
        assessmentMarks=intent.getStringExtra("assessment_marks");
        assessmentName=intent.getStringExtra("assessment_Name");

        loadingPB = findViewById(R.id.idPBLoading);
        loadingPB.setVisibility(View.VISIBLE);

        startButton=findViewById(R.id.start_assessment_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                UpdateData();
                Intent intent=new Intent(getApplicationContext(), Theory_assessment_Activity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();

            }
        });


        /////////////////////
//        RecyclerView recyclerView;
//        ArrayList<ParentModel> mainQuestionList;
//        ArrayList<ChildModel> subQuestionList;
        mainQuestionList=new ArrayList<>();
        subQuestionList=new ArrayList<>();


        getAssessmentData();
    }



    private void UpdateData() {

        JSONObject requestData = new JSONObject();
        try {
           // requestData.put("user_id", Navigation_Drawer_Activity.userId);
            requestData.put("user_id", "100006");
            requestData.put("assessment_id","100005");
            requestData.put("total_marks", assessmentMarks);
            requestData.put("ass_start_date", "2023-10-06");
            requestData.put("result_status", "Pending");
            requestData.put("status", "active");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url,requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle success response from the server
                        try {
                            String message = response.getString("message");
                            Toast.makeText(getApplicationContext(), "Assessment started", Toast.LENGTH_SHORT).show();
                            System.out.println("Error Status Code: " + "ssssss");
                            // Display or handle the message as needed
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
                Log.d("beartoken",Navigation_Drawer_Activity.BearerToken);
                headers.put("Authorization", "Bearer " +"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzaHViaGFtaGFkYXdhbGVAZ21haWwuY29tIiwiZXhwIjoxNzAwNjczMjY4fQ.l0g-Swvn-FyUyWC8tyVwWG-6Ot0Vw4EBtPhoZ9c3qL4");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);

    }

    private void getAssessmentData() {

        String Url="http://flask-medhvrushti.checkerslab.com/generate-question-paper";
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("subject_id", "100006");
            //     requestData.put("lastName", "100002");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        // Handle success response from the server
                        try {
                            JSONArray assQuestionArray = response.getJSONArray("questions");
                            JSONArray assQueTypeArray = response.getJSONArray("format");
                            mainQuestionList.clear();

                            int initialCount = 0;
                            for (int i = 0; i < assQueTypeArray.length(); i++) {
                                JSONObject typeObject = assQueTypeArray.getJSONObject(i);
                                String questionType = typeObject.getString("question_type_name");
                                String subQuestion_type = typeObject.getString("question_category");
                                String questionCount = typeObject.getString("total_question");
                                String totalMarks=typeObject.getString("total_marks");
                                String questionNumbers=typeObject.getString("question_number");
                                String subQuestionMark=typeObject.getString("per_ques_marks");

                                int sublistSize = Integer.parseInt(questionCount);
                                List<ChildModel> currentSubQuestionList = new ArrayList<>(); // Create a new list for each main question

                                for (int j = initialCount; j < initialCount + sublistSize; j++) {
                                    JSONObject questionsObject = assQuestionArray.getJSONObject(j);
                                    String questionId = questionsObject.getString("question_id");

                                    String questionLatex = questionsObject.getString("question_line_by_latex");
                                    String option1 = questionsObject.getString("option1_latex");
                                    String option2 = questionsObject.getString("option2_latex");
                                    String option3 = questionsObject.getString("option3_latex");
                                    String option4 = questionsObject.getString("option4_latex");
                                    String marks = questionsObject.getString("marks");



                                    if (subQuestion_type.equals("MCQ_Questions"))
                                    {
                                        currentSubQuestionList.add(new ChildModel(questionId,questionLatex,subQuestion_type,option1,option2,option3,option4,marks,ChildModel.LayoutOne));

                                    }
                                    else
                                    {
                                        currentSubQuestionList.add(new ChildModel(questionId,questionLatex,subQuestion_type,option1,option2,option3,option4,marks,ChildModel.LayoutTwo));

                                    }


                                }

                                initialCount += sublistSize;

                                mainQuestionList.add(new ParentModel(questionType,totalMarks,subQuestionMark,questionNumbers,currentSubQuestionList));
                                loadingPB.setVisibility(View.GONE);
                                startButton.setVisibility(View.VISIBLE);
                            }

//                            parentAdapter = new ParentAdapter(mainQuestionList, getApplicationContext());
//                            recyclerView.setAdapter(parentAdapter);

                            Log.d("count", String.valueOf(mainQuestionList.size()) + " and question count" + subQuestionList.size());


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
                            loadingPB.setVisibility(View.GONE);
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
