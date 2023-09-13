package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import java.util.List;

public class Ass_Topic_activity extends AppCompatActivity {


    RecyclerView recyclerView;

    List<Ass_Topic_model> topicList;
    static  String Chapter_id;
    private String Url="http://192.168.50.67:9191/api/v1/cil/topics/get-topics?chapterId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_topic);

        recyclerView=findViewById(R.id.Assessment_Topic_recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));


        topicList=new ArrayList<>();
        Intent intent=getIntent();
        Chapter_id=intent.getStringExtra("Ass_chapter_id").toString();
        Toast.makeText(this, Chapter_id, Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, Url+Chapter_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        Ass_Topic_model model=new Ass_Topic_model(object.getInt("topicId"),
                                object.getInt("totalQuestion"),
                                object.getString("topicCode"),
                                object.getString("topicName"));

                        topicList.add(model);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                Ass_Topic_Adapter adapter=new Ass_Topic_Adapter(topicList,getApplicationContext());
                recyclerView.setAdapter(adapter);

                //Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(arrayRequest);

    }
}