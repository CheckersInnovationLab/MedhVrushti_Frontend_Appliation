package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.CompetitivePkg.Competitive_Assessment_Main_Screen2;
import com.example.checkerslab_edulearning.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Test_Reminder_activity extends AppCompatActivity {

    Button ready;

    public static ArrayList<Selected_Test_Data_Model> testDataList;
    String topicId;
    private String Url="http://89.116.33.21:1121/dummy-question-app/api/questions/get/all";
    public static final int Not_Visited=0;
    public static final int UnAnswered=1;
    public static final int Answered=2;
    public static final int Review=3;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_reminder);


        testDataList=new ArrayList<>();
//          Intent intent=getIntent();
//          topicId=intent.getStringExtra("Ass_topic_id");

      //  Toast.makeText(this, "topicId", Toast.LENGTH_SHORT).show();






        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        Selected_Test_Data_Model model=new Selected_Test_Data_Model(object.getInt("id"),
                                object.getInt("id"),
                                object.getString("question"),
                                object.getString("question"),
                                object.getString("option1"),
                                object.getString("option2"),
                                object.getString("option3"),
                                object.getString("option4"),
                                object.getString("answer"),
                                object.getString("description"),
                                "Not_Visited",
                                ""

                        );

                        Log.d("questions",object.getString("question"));
                     //   Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();


                        testDataList.add(model);
                    }catch (Exception e)
                    {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                }

                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            //    Toast.makeText(getApplicationContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
              //  Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(arrayRequest);


        ready=findViewById(R.id.are_you_ready_button);
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Test_Reminder_activity.this, Competitive_Assessment_Main_Screen2.class);
                startActivity(intent);
            }
        });

    }
}