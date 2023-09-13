package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

public class Ass_Subjects_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    //DatabaseReference databaseReference;
    List<Ass_Subjects_Model> SubjectList;
    public static String Ass_std_id;
    private String Url="http://192.168.50.67:9191/api/v1/cil/subject/get-all-subjects?standardId=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_subjects);

        recyclerView=findViewById(R.id.Ass_Subjects_recyclerView_id);
        SubjectList=new ArrayList<>();
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);

        Intent intent = getIntent();
        Ass_std_id = intent.getStringExtra("Ass_standard_id");

        Toast.makeText(this, Ass_std_id, Toast.LENGTH_SHORT).show();

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, Url+Ass_std_id, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        Ass_Subjects_Model model=new Ass_Subjects_Model(object.getInt("subjectId"),
                                object.getInt("totalChapters"),
                                object.getString("subjectCode"),
                                object.getString("subjectName"));

                        SubjectList.add(model);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
               Ass_Subjects_Adapter adapter=new Ass_Subjects_Adapter(SubjectList,getApplicationContext());
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