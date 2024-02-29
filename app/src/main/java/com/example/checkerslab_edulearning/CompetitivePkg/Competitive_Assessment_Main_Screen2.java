package com.example.checkerslab_edulearning.CompetitivePkg;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Selected_Test_Data_Model;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Test_Reminder_activity;
import com.example.checkerslab_edulearning.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Competitive_Assessment_Main_Screen2 extends AppCompatActivity {


    private List<Selected_Test_Data_Model> assessmentDataList;
    private String Url = "http://89.116.33.21:1121/dummy-question-app/api/questions/get/all";
    private RecyclerView recyclerView;
    LinearLayoutManager horizontalLayout;
    competitiveAssAdapter competitiveAssAdapter;
    private RelativeLayout nextButton, previousButton;
    private int currentQuesNO = 0;
    TextView questionNumber;
    private TextView questionNumberT, time,submit;
    private CountDownTimer countDownTimer;
    ImageView questionStatusB;
    private DrawerLayout drawerLayout;
    private ImageView closeB;
    private GridView gridView;
    private questStatusGridAdapter gridAdapter;
    int totalQuestion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comp_ass_questions_status_layout);

        //questionNumberT=findViewById(R.id.Competitive_Assessment_number_text_id);



        recyclerView = findViewById(R.id.competitive_recycler_view_id);
        nextButton = findViewById(R.id.Competitive_Assessment_next_layout);
        previousButton = findViewById(R.id.Competitive_Assessment_previous_layout);
        time = findViewById(R.id.Competitive_Assessment_time_text_id);
        questionNumber = findViewById(R.id.Competitive_Assessment_number_text_id);


        questionStatusB = findViewById(R.id.Competitive_Assessment_view_all_id);

        drawerLayout = findViewById(R.id.drawer_layout_id);
        closeB = findViewById(R.id.close_button_id1212);
        gridView = findViewById(R.id.Grid_view_id);
        totalQuestion = Test_Reminder_activity.testDataList.size();
        gridAdapter = new questStatusGridAdapter(totalQuestion, this);
        gridView.setAdapter(gridAdapter);
        submit=findViewById(R.id.Competitive_Assessment_submit_button_id);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Competitive_Assessment_Main_Screen2.this,Competitive_Ass_Result_Screen.class);
                startActivity(intent);
            }
        });


        horizontalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.HORIZONTAL,
                true);

        recyclerView.setLayoutManager(horizontalLayout);
        competitiveAssAdapter = new competitiveAssAdapter(Test_Reminder_activity.testDataList, getApplicationContext());

        recyclerView.setAdapter(competitiveAssAdapter);

        //  setSnapHelper();

        setClickListener();
        startTimer();
        setQuesNumber(currentQuesNO);


        questionStatusB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((!drawerLayout.isDrawerOpen(GravityCompat.END))) {
                    gridAdapter.notifyDataSetChanged();
                    drawerLayout.openDrawer(GravityCompat.END);
                }
            }
        });
//        closeB.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if ((drawerLayout.isDrawerOpen(GravityCompat.END)))
//                {
//                    drawerLayout.closeDrawer(GravityCompat.END);
//                }
//            }
//        });
    }

    private void setQuesNumber(int currentQuesNO) {
        questionNumber.setText(String.valueOf(currentQuesNO + 1));

    }

    private void startTimer() {

        long totalTimeInMillis = 10 * 60 * 1000;

        countDownTimer = new CountDownTimer(totalTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timer display on each tick
                updateTimerDisplay(millisUntilFinished);
            }

            @Override
            public void onFinish() {
                // Handle the timer finish event (e.g., quiz submission)
                time.setText("Timer: 00:00");
                // Add any actions to perform when the timer finishes
            }
        };

        // Start the countdown timer
        countDownTimer.start();
    }

    private void updateTimerDisplay(long millisUntilFinished) {
        // Convert milliseconds to minutes and seconds
        int minutes = (int) (millisUntilFinished / 1000) / 60;
        int seconds = (int) (millisUntilFinished / 1000) % 60;

        // Format the time and update the TextView
        String timeLeftFormatted = String.format("%02d:%02d", minutes, seconds);
        time.setText("Timer: " + timeLeftFormatted);
    }


//    private void LoadAssessmentData() {
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i = 0; i <= response.length() - 1; i++) {
//                    try {
//                        JSONObject object = response.getJSONObject(i);
//                        Selected_Test_Data_Model model = new Selected_Test_Data_Model(object.getInt("id"),
//                                object.getInt("id"),
//                                object.getString("question"),
//                                object.getString("question"),
//                                object.getString("option1"),
//                                object.getString("option2"),
//                                object.getString("option3"),
//                                object.getString("option4"),
//                                object.getString("answer"),
//                                object.getString("description"),
//                                "Not_Visited",
//                                "",
//                                "No",
//                                object.getString("question_diagrams_url"),
//                                object.getString("description_diagrams_url")
//
//                        );
//
//                        assessmentDataList.add(model);
//                    } catch (Exception e) {
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
//
//                        e.printStackTrace();
//                    }
//                }
//
//                competitiveAssAdapter = new competitiveAssAdapter(assessmentDataList, getApplicationContext());
//                recyclerView.setAdapter(competitiveAssAdapter);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//            }
//        });
//        requestQueue.add(arrayRequest);
//    }


//    private void setSnapHelper() {
//
//        SnapHelper snapHelper=new PagerSnapHelper();
//        snapHelper.attachToRecyclerView(recyclerView);
//
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//
//                View view=snapHelper.findSnapView(recyclerView.getLayoutManager());
//                currentQuesNO=recyclerView.getLayoutManager().getPosition(view);
//                questionNumber.setText(String.valueOf(currentQuesNO));
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//            }
//        });
//    }
//


    private void setClickListener() {


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuesNO < Test_Reminder_activity.testDataList.size() - 1) {

                    currentQuesNO++; // Increment first
                    recyclerView.smoothScrollToPosition(currentQuesNO);
                    Log.d("Next button Clicked:", "1 times");
                }

                setQuesNumber(currentQuesNO);
            }
        });

        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuesNO > 0) {

                    currentQuesNO--; // Decrement first
                    recyclerView.smoothScrollToPosition(currentQuesNO);
                    competitiveAssAdapter.notifyDataSetChanged();
                }
                setQuesNumber(currentQuesNO);
            }
        });

    }

    public void gotoQuestion(int position) {
        recyclerView.smoothScrollToPosition(position);
        currentQuesNO=position;
        setQuesNumber(currentQuesNO);

        if ((drawerLayout.isDrawerOpen(GravityCompat.END)))
        {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        Log.d("Debug", "Selected position: " + position);

        // Notify the adapter about the dataset change
        gridAdapter.notifyDataSetChanged();

        if ((drawerLayout.isDrawerOpen(GravityCompat.END))) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }


    }
}