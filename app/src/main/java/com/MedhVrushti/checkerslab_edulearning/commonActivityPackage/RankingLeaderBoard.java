package com.MedhVrushti.checkerslab_edulearning.commonActivityPackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.MedhVrushti.checkerslab_edulearning.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class RankingLeaderBoard extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<RankingModel> rankingList;
    private LinearLayoutManager verticalLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking_leader_board);
        recyclerView=findViewById(R.id.RankingLeaderBoard_recyclerView_id);
        verticalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false);
        recyclerView.setLayoutManager(verticalLayout);

        rankingList=new ArrayList<>();

        Intent intent=getIntent();
        String assessmentId=intent.getStringExtra("assessmentId");

        getRankingDetails(assessmentId);

    }

    private void getRankingDetails(String assessmentId) {

        Log.d("getRankingDetails","getRankingDetails1");
        String url= "http://89.116.33.21:5000/cet/all/Ranking?assessment_id="+assessmentId;

        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {

                    try {

                        JSONObject object=response.getJSONObject(i);
                        RankingModel model=new RankingModel(
                                object.getString("user_name"),
                                object.getString("assessment_id"),
                                String.valueOf(object.getInt("rank")),
                                String.valueOf(object.getInt("obtained_marks")));
                        Log.d("getRankingDetails2222",String.valueOf(rankingList.size()));
                        rankingList.add(model);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                Log.d("getRankingDetails",String.valueOf(rankingList.size()));


                RankingAdapter rankingAdapter=new RankingAdapter(rankingList,getApplicationContext());
                recyclerView.setAdapter(rankingAdapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("getRankingDetails","getRankingDetails4");
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(arrayRequest);

    }
}