package com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.commonActivityPackage.AllAssessmentAdapter;
import com.example.checkerslab_edulearning.commonActivityPackage.AllAssessmentModel;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseChapterAdapter;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseChapterModel;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseSubjectsActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Assessment_home_Screen extends AppCompatActivity {

    public static int finalMCQAssessmentCount;
    public static int chapterMCQAssessmentCount;
    public static String SubjectId="",subjectName="";
    private RelativeLayout finalAssTab,chapterAssTab,uniteAssTab,selfAssTab;
    public static ArrayList<AllAssessmentModel> finalAssessmentList;
    public static ArrayList<CourseChapterModel> courseChapterList;
    private ProgressBar assessmentHomePb;
    private TextView assSubjectName;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_home_screen);


//        Intent intent=getIntent();
//        SubjectId=intent.getStringExtra("Subject_id").toString();
//        subjectName=intent.getStringExtra("subject_Name").toString();
        SubjectId= CourseSubjectsActivity.subjectId;
        subjectName=CourseSubjectsActivity.SubjectName;
        finalAssTab=findViewById(R.id.final_assessment_button_id);
        chapterAssTab=findViewById(R.id.chapter_assessment_button_id);
        uniteAssTab=findViewById(R.id.Unite_assessment_button_id);
        selfAssTab=findViewById(R.id.Self_generated_assessment_button_id);
        assessmentHomePb=findViewById(R.id.assessment_home_pbLoading);
        assessmentHomePb.setVisibility(ProgressBar.VISIBLE);

        assSubjectName=findViewById(R.id.Courses_allAss_subject_text_id);
        assSubjectName.setText(subjectName);

        backButton=findViewById(R.id.Courses_all_ass_back_button_id);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getSubscriptionDetails(SubjectId);
        chapterAssTab.setBackgroundColor(Color.GRAY);

        finalAssessmentList=new ArrayList<>();
        courseChapterList=new ArrayList<>();


    }

    private void setChapterFragments() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.layout,new ChapterWise_Assessment_Tab()).commit();
    }

    private void getSubscriptionDetails(String SubjectIdL) {

      String Url="http://89.116.33.21:5000/get-subscription-access-data";

        JSONObject requestData = new JSONObject();
        try {
            requestData.put("bearer_token", StaticFile.bearToken);
            requestData.put("subject_id", SubjectIdL);
            requestData.put("user_id", StaticFile.userId);

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
                            JSONArray jsonArray = response.getJSONArray("assesments");
                            Log.d("abcd","success");

                            int initialCount = 0;
                            for (int i = 0; i < jsonArray.length(); i++) {

                                JSONObject typeObject = jsonArray.getJSONObject(i);
                                if (typeObject.getString("subscription_category") .equals("combined")) {
                                    String questionType = typeObject.getString("books");
                                    finalMCQAssessmentCount = Integer.valueOf(typeObject.getString("mcq_over_all_ass_count"));
                                    chapterMCQAssessmentCount = Integer.valueOf(typeObject.getString("mcq_per_chapter_ass_count"));
                                    String totalMarks = typeObject.getString("notes");
                                    String questionNumbers = typeObject.getString("subscription_category");
                                    String subQuestionMark = typeObject.getString("theory_over_all_ass_count");
                                    String subQuestion = typeObject.getString("theory_per_chapter_ass_count");
                                }

                            }
                            if (finalMCQAssessmentCount!=0)
                            {
                               getFinalAssessment();
                            }
                             if (chapterMCQAssessmentCount!=0)
                            {
                                Log.d("getChapterDetails","110");
                                getChapterDetails();

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
                           Log.d("abcd",errorMessage);
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

    public void onClick(View v){

        finalAssTab.setBackgroundColor(Color.parseColor("#EBEDF8"));
        chapterAssTab.setBackgroundColor(Color.parseColor("#EBEDF8"));
        uniteAssTab.setBackgroundColor(Color.parseColor("#EBEDF8"));
        selfAssTab.setBackgroundColor(Color.parseColor("#EBEDF8"));
        switch (v.getId()){

            case R.id.final_assessment_button_id:
                finalAssTab.setBackgroundColor(Color.GRAY);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout,
                                new Final_Assessment_Tab()).commit();

                break;
            case R.id.Unite_assessment_button_id:
                uniteAssTab.setBackgroundColor(Color.GRAY);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout,
                                new Unite_Assessment_Tab()).commit();
                break;
            case R.id.chapter_assessment_button_id:
                chapterAssTab.setBackgroundColor(Color.GRAY);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout,
                                new ChapterWise_Assessment_Tab()).commit();
                break;

            case R.id.Self_generated_assessment_button_id:
                selfAssTab.setBackgroundColor(Color.GRAY);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.layout,
                                new SelfGenerated_Ass_tab()).commit();
                break;

        }
    }

    private void getFinalAssessment() {

//        String assessmentUrl= "http://89.116.33.21:5000/cet/assessment/status";
       // String assessmentUrl= "http://89.116.33.21:5000/cet/assessment/get/all/by/status?user_id="+StaticFile.userId+"&subject_id="+SubjectId;

             String assessmentUrl= "https://medhvrushti.checkerslab.com/api/v1/cil/assessments/get/all/by/subject_id/and/user_id?subject_id="+SubjectId+"&user_id="+StaticFile.userId;
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, assessmentUrl,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                //  Toast.makeText(getContext(), object.getString("assessment_category"), Toast.LENGTH_SHORT).show();

                                if (object.getString("assessment_category").equals("FINAL ASSESSMENT") ||object.getString("assessment_category")=="FINAL ASSESSMENT")
                                {
                                    if (0<finalMCQAssessmentCount)
                                    {
                                        AllAssessmentModel model=new AllAssessmentModel(
                                                object.getString("assessment_name"),
                                                object.getString("total_marks"),
                                                object.getString("assessment_id"),
                                                object.getString("assessment_status"));
                                        finalAssessmentList.add(model);
                                        finalMCQAssessmentCount--;
                                    }
                                }



                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        assessmentHomePb.setVisibility(ProgressBar.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            assessmentHomePb.setVisibility(ProgressBar.GONE);
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

    private void getChapterDetails() {

        Log.d("getChapterDetails","111");

        String url= StaticFile.Url+"/api/v1/cil/chapter/get/all/by/subject_id?subject_id="+SubjectId;

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);

                        CourseChapterModel model=new CourseChapterModel(
                                object.getString("chapter_id"),
                                object.getString("chapter_code"),
                                object.getString("chapter_name"),
                                object.getString("subject_id"),
                                object.getString("total_topics"),
                                object.getString("created_by"),
                                object.getString("creation_date"),
                                object.getString("last_updation_date"),
                                object.getString("last_update_by"),
                                object.getString("status"));


                        courseChapterList.add(model);

                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                assessmentHomePb.setVisibility(ProgressBar.GONE);
                Log.d("getChapter",String.valueOf(courseChapterList.size()));
                setChapterFragments();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
                    assessmentHomePb.setVisibility(ProgressBar.GONE);
                    int statusCode = error.networkResponse.statusCode;
                    byte[] errorResponseData = error.networkResponse.data; // Error response data
                    String errorMessage = new String(errorResponseData); // Convert error data to string
                    // Print the error details
                    System.out.println("Error Status Code: " + statusCode);
                    System.out.println("Error Response Data: " + errorMessage);
                }
            }
        })
        {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };
        requestQueue.add(arrayRequest);
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        finish();
//    }
}