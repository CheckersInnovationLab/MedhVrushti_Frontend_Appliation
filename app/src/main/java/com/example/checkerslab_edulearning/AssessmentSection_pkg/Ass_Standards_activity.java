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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Ass_Standards_activity extends AppCompatActivity {


    RecyclerView recyclerView;
    //DatabaseReference databaseReference;
    List<Ass_Standards_Model> standardsList;
    static String ASS_MainBoardID;
    private String Url="http://192.168.50.67:9191/api/v1/cil/standard/get-all-standards?boardId=";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ass_standards);

        recyclerView=findViewById(R.id.Ass_standards_recyclerView_id);
        standardsList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Intent intent = getIntent();
        ASS_MainBoardID = intent.getStringExtra("Ass_Board_ID");
        Toast.makeText(this, ASS_MainBoardID, Toast.LENGTH_SHORT).show();


        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, Url+ASS_MainBoardID, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        Ass_Standards_Model model=new Ass_Standards_Model(object.getInt("stdId"),
                                object.getInt("totalSubjects"),
                                object.getString("stdName"),
                                object.getString("stdName"));

                        standardsList.add(model);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
               Ass_standards_adapter adapter=new Ass_standards_adapter(standardsList,getApplicationContext());
                recyclerView.setAdapter(adapter);

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
     });
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                // below line we are creating a map for
//                // storing our values in key and value pair.
//                Map<String, String> params = new HashMap<String, String>();
//
//                // on below line we are passing our key
//                // and value pair to our parameters.
//                params.put("boardId","100002");
//
//                // at last we are
//                // returning our params.
//                return params;
//            }
//        };
        // below line is to make
        // a json object request.

        requestQueue.add(arrayRequest);



    }
}