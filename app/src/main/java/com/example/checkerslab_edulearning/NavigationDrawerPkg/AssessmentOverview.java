package com.example.checkerslab_edulearning.NavigationDrawerPkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentModel;
import com.example.checkerslab_edulearning.storePackage.storeCoursesParentAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AssessmentOverview extends AppCompatActivity {



    RecyclerView recyclerView;
    ArrayList<AssessmentOverviewModel> subQuestionList;
    LinearLayoutManager HorizontalLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_overview);


        recyclerView=findViewById(R.id.assessment_overview_recycler_id);
        HorizontalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(HorizontalLayout);
        subQuestionList=new ArrayList<>();

        getData();
    }

    private void getData() {

        String Url="http://flask-medhvrushti.checkerslab.com/get-checked-assessment";
        JSONObject requestData = new JSONObject();
        try {
            requestData.put("user_id", "100003");
                requestData.put("assessment_id", "100005");
            requestData.put("bearer_token", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzYWNoaW5AZ21haWwuY29tIiwiZXhwIjoxNzAwNzI0NTc2fQ.vsoRzq7dYzdxwqM-z5EJ4bippatbpYZm_O-1bZX1_Rc");

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
                            JSONArray assQuestionArray = response.getJSONArray("student_answers");
                            JSONArray assQueTypeArray = response.getJSONArray("format");
//                            mainQuestionList.clear();
                            List<AssessmentOverviewModel> currentSubQuestionList = new ArrayList<>(); // Create a new list for each main question

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

                                for (int j = initialCount; j < initialCount + sublistSize; j++) {
                                    JSONObject questionsObject = assQuestionArray.getJSONObject(j);
                                    String questionId = questionsObject.getString("question_id");

                                    String questionLatex = questionsObject.getString("question_line_by_latex");
                                    String quesNo = questionsObject.getString("question_number");
                                    String ansoverview = questionsObject.getString("remarks");
                                    String ansImg = questionsObject.getString("answer_img_url");
                                    String totalMarksP = questionsObject.getString("total_marks");
                                    String obtainedMarksP = questionsObject.getString("obtained_marks");


                                    currentSubQuestionList.add(new AssessmentOverviewModel(quesNo,questionLatex,ansoverview,ansImg,totalMarksP,obtainedMarksP));


                                    AssessmentOverviewAdapter assessmentOverviewAdapter = new AssessmentOverviewAdapter(currentSubQuestionList, getApplicationContext());
                                    recyclerView.setAdapter(assessmentOverviewAdapter);

                                }

                                initialCount += sublistSize;

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