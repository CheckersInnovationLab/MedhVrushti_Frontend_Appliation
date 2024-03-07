package com.example.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.CompetitivePkg.CompetetiveAssessmentScreen;
import com.example.checkerslab_edulearning.CompetitivePkg.Competitive_Ass_Result_Screen;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainFragment;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

public class CourseSubjectsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<CourseSubjectModel> subjectModelArrayList;
    static String subscription_id;
    private ImageView backButton;
    private ProgressBar courseSubjectPb;
    private String url="https://medhvrushti.checkerslab.com/api/v1/cil/user_subscriptions/get/all/by/subscription_id_and_user_id?";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_subjects);
        recyclerView=findViewById(R.id.Courses_subject_recycler_id);
        backButton=findViewById(R.id.Courses_subject_back_button_id);
        courseSubjectPb=findViewById(R.id.course_subject_pbLoading);

        Intent intent=getIntent();
        subscription_id=intent.getStringExtra("Subscription_id");
        courseSubjectPb.setVisibility(ProgressBar.VISIBLE);
//        subscription_id="100008";

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        url=url+"subscription_id="+subscription_id+"&user_id="+ StaticFile.userId;



        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        subjectModelArrayList=new ArrayList<>();


        AddItemsToTopCatRecyclerView();
    }

    private void AddItemsToTopCatRecyclerView() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object=response.getJSONObject(i);

                                CourseSubjectModel model=new CourseSubjectModel(
                                        object.getString("user_subscription_id"),
                                        object.getString("user_id"),
                                        object.getString("subscription_id"),
                                        object.getString("standard_id"),
                                        object.getString("subject_id"),
                                        object.getString("subject_name"),
                                        object.getString("attribute1"));

                                subjectModelArrayList.add(model);
                            }catch (Exception e)
                            {
                                e.printStackTrace();
                                Toast.makeText(CourseSubjectsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        CourseSubjectAdapter adapter=new CourseSubjectAdapter(subjectModelArrayList,getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        courseSubjectPb.setVisibility(ProgressBar.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                           courseSubjectPb.setVisibility(ProgressBar.GONE);
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