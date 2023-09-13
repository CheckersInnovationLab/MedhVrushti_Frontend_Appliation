package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Test_Reminder_activity extends AppCompatActivity {

    Button ready;



    public static ArrayList<Selected_Test_Data_Model> testDataList;
    String topicId;
    private String Url="http://192.168.50.67:9191/api/v1/cil/questions/get-all-by-topic-id?topicId=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_reminder);


        testDataList=new ArrayList<>();
          Intent intent=getIntent();
          topicId=intent.getStringExtra("Ass_topic_id");


        Toast.makeText(this, topicId, Toast.LENGTH_SHORT).show();
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, Url+topicId, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        Selected_Test_Data_Model model=new Selected_Test_Data_Model(object.getInt("questionId"),
                                object.getInt("marks"),
                                object.getString("questionType"),
                                object.getString("question"),
                                object.getString("option1"),
                                object.getString("option2"),
                                object.getString("option3"),
                                object.getString("option4"),
                                object.getString("answer"),
                                object.getString("answerDescription"));


                        testDataList.add(model);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(arrayRequest);


        ready=findViewById(R.id.are_you_ready_button);
        ready.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Test_Reminder_activity.this,Assessment_Screen.class);
                startActivity(intent);
            }
        });

    }
}