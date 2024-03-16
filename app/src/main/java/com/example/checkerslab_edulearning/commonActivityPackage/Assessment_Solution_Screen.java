package com.example.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Selected_Test_Data_Model;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Assessment_Solution_Screen extends AppCompatActivity {

    RecyclerView recyclerView;
    Selected_Test_Data_Model modelClass;
    TextView toolbarTitle;
    ImageView back;
    public static ArrayList<Selected_Test_Data_Model> testDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_solution_screen);


        Intent intent=getIntent();
        String assessment_id=intent.getStringExtra("assessment_id");

        Toolbar toolbar=findViewById(R.id.assessment_solution_toolbar_id);
        setSupportActionBar(toolbar);
        toolbarTitle=findViewById(R.id.assessment_solution_Toolbar_title);
        back=findViewById(R.id.assessment_solution_Toolbar_back_button);
        toolbarTitle.setText("Assessment Solution");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        recyclerView=findViewById(R.id.Assessment_solution_recyclerView);
        testDataList=new ArrayList<>();
//
//        Assessment_solution_adapter myAdapter = new Assessment_solution_adapter(Test_Reminder_activity.testDataList, getApplicationContext());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setAdapter(myAdapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getAssessmentSolutions(assessment_id);

    }

    private void getAssessmentSolutions(String assessment_id) {

        Log.d("getAssessmentSolutions","Log1");

        String Url = "http://89.116.33.21:5000/user/assessment/detail";
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", Integer.valueOf(StaticFile.userId));
            requestData.put("assessment_id", Integer.valueOf(100015));
            requestData.put("bearer_token", StaticFile.bearToken);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String jsonString=requestData.toString();
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, requestData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("getAssessmentSolutions","Log2");


                        try {
                            JSONArray jsonArray = response.getJSONArray("question_paper");

                            for (int n = 0; n < jsonArray.length(); n++) {
                                Log.d("QuestionPaperGenerator", "log4");
                                JSONObject typeObject = jsonArray.getJSONObject(n);

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
                                            question.getString("question_diagrams_url"),
                                            question.getString("description_diagrams_url"),
                                            question.getString("question_type_id")
                                    );

                                    testDataList.add(model);
                                    Log.d("QuestionPaperGenerator", "log6");

                                }


                                //
                                    Assessment_solution_adapter myAdapter = new Assessment_solution_adapter(testDataList, getApplicationContext());
                                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    recyclerView.setAdapter(myAdapter);

//                                questionPaperLoadingBar.setVisibility(View.GONE);
//                                StartButton.setVisibility(View.VISIBLE);
//                                Log.d("QuestionPaperGenerator", "log7");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  Log.d("getAssessmentSolutions",error.getMessage());
                        byte[] errorResponseData = error.networkResponse.data; // Error response data
                        String errorMessage = new String(errorResponseData);
                        Log.d("getAssessmentSolutions", errorMessage);

//                        // Handle error response
//                        if (error.networkResponse != null) {
////                            questionPaperLoadingBar.setVisibility(View.GONE);
//                            int statusCode = error.networkResponse.statusCode;
//                            byte[] errorResponseData = error.networkResponse.data; // Error response data
//                            String errorMessage = new String(errorResponseData); // Convert error data to string
//                            // Print the error details
//                            Log.d("QuestionPaperGenerator", errorMessage);
//                        }
                    }
                }
        ) {


            @Override
            public String getBodyContentType() {
                return "application/json;charset=utf-8";
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


}