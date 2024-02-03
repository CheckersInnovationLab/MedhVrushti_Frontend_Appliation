package com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_Standards_Model;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_standards_adapter;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildAdapter;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentAdapter;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentModel;
import com.example.checkerslab_edulearning.commonActivityPackage.Assessment_Instruction_Activity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Theory_assessment_Activity extends AppCompatActivity {

    RecyclerView recyclerView;
//    ArrayList<ParentModel> mainQuestionList;
//    ArrayList<ChildModel> subQuestionList;
    private String Url="http://flask-medhvrushti.checkerslab.com/generate-question-paper";

    ParentAdapter parentAdapter;

    TextView assessmentName,totalMarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theory_assessment);


        recyclerView=findViewById(R.id.Theory_assessment_Main_recyclerView_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        assessmentName=findViewById(R.id.Theory_Assessment_name_id);
        totalMarks=findViewById(R.id.Theory_Assessment_total_marks_id);
        assessmentName.setText(Assessment_Instruction_Activity.assessmentName);
        totalMarks.setText("Total Marks: "+Assessment_Instruction_Activity.assessmentMarks);

//        mainQuestionList=new ArrayList<>();
//        subQuestionList=new ArrayList<>();
        parentAdapter = new ParentAdapter(Assessment_Instruction_Activity.mainQuestionList, getApplicationContext());
        recyclerView.setAdapter(parentAdapter);
        getAssessmentData();
    }
    private void getAssessmentData() {
//
        /////////////////////////////////////////////////////////

//        JSONObject requestData = new JSONObject();
//        try {
//            requestData.put("subject_id", "100006");
//            //     requestData.put("lastName", "100002");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Url, requestData,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//
//                        // Handle success response from the server
//                        try {
//                            JSONArray assQuestionArray = response.getJSONArray("questions");
//                            JSONArray assQueTypeArray = response.getJSONArray("format");
//                            mainQuestionList.clear();
//
//                            int initialCount = 0;
//                            for (int i = 0; i < assQueTypeArray.length(); i++) {
//                                JSONObject typeObject = assQueTypeArray.getJSONObject(i);
//                                String questionType = typeObject.getString("question_type_name");
//                                String subQuestion_type = typeObject.getString("question_category");
//                                String questionCount = typeObject.getString("total_question");
//                                String totalMarks=typeObject.getString("total_marks");
//                                String questionNumbers=typeObject.getString("question_number");
//                                String subQuestionMark=typeObject.getString("per_ques_marks");
//
//                                int sublistSize = Integer.parseInt(questionCount);
//                                List<ChildModel> currentSubQuestionList = new ArrayList<>(); // Create a new list for each main question
//
//                                for (int j = initialCount; j < initialCount + sublistSize; j++) {
//                                    JSONObject questionsObject = assQuestionArray.getJSONObject(j);
//                                    String questionId = questionsObject.getString("question_id");
//
//                                    String questionLatex = questionsObject.getString("question_line_by_latex");
//                                    String option1 = questionsObject.getString("option1_latex");
//                                    String option2 = questionsObject.getString("option2_latex");
//                                    String option3 = questionsObject.getString("option3_latex");
//                                    String option4 = questionsObject.getString("option4_latex");
//                                    String marks = questionsObject.getString("marks");
//
//
//
//                                    if (subQuestion_type.equals("MCQ_Questions"))
//                                    {
//                                        currentSubQuestionList.add(new ChildModel(questionId,questionLatex,subQuestion_type,option1,option2,option3,option4,marks,ChildModel.LayoutOne));
//
//                                    }
//                                    else
//                                    {
//                                        currentSubQuestionList.add(new ChildModel(questionId,questionLatex,subQuestion_type,option1,option2,option3,option4,marks,ChildModel.LayoutTwo));
//
//                                    }
//
//
//                                }
//
//                                initialCount += sublistSize;
//
//                                mainQuestionList.add(new ParentModel(questionType,totalMarks,subQuestionMark,questionNumbers,currentSubQuestionList));
//                            }
//
//                            parentAdapter = new ParentAdapter(mainQuestionList, getApplicationContext());
//                            recyclerView.setAdapter(parentAdapter);
//
//                           Log.d("count", String.valueOf(mainQuestionList.size()) + " and question count" + subQuestionList.size());
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
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
//                            System.out.println("Error Status Code: " + statusCode);
//                            System.out.println("Error Response Data: " + errorMessage);
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





}
