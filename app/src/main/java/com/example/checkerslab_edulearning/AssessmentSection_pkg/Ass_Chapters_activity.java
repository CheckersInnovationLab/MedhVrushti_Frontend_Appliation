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

public class Ass_Chapters_activity extends AppCompatActivity {

    RecyclerView recyclerView;

    List<Ass_Chapters_model> Chaplist;
    static String Ass_Sub_id;
    private String Url="http://192.168.50.67:9191/api/v1/cil/chapter/get-all-chapters?subjectId=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_chapters);

        recyclerView=findViewById(R.id.Ass_chapters_recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



        Chaplist=new ArrayList<>();
        Intent intent=getIntent();
        Ass_Sub_id=intent.getStringExtra("Ass_Subject_id");


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, Url+Ass_Sub_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        Ass_Chapters_model model=new Ass_Chapters_model(object.getInt("chapterId"),
                                object.getInt("totalTopics"),
                                object.getString("chapterCode"),
                                object.getString("chapterName"));

                        Chaplist.add(model);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                Ass_Chapters_Adapter adapter=new Ass_Chapters_Adapter(Chaplist,getApplicationContext());
                recyclerView.setAdapter(adapter);

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();

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