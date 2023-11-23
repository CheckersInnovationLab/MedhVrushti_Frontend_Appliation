package com.example.checkerslab_edulearning.commonActivityPackage;

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
import java.util.Map;

public class CourseChaptersActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<CourseChapterModel> courseChapterList;
    LinearLayoutManager VerticalLayout;
    static String subject_id;
    private String url="http://apis-medhvrushti.checkerslab.com/api/v1/cil/chapter/get/all/by/subject_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_chapters);


        Intent intent=getIntent();
        subject_id=intent.getStringExtra("Subject_id");
      //  Toast.makeText(this, subject_id, Toast.LENGTH_SHORT).show();

        recyclerView=findViewById(R.id.Course_chapter_recyclerView_id);
        courseChapterList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getApplicationContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);
        AddItemsToTopCatRecyclerView();
    }


    private void AddItemsToTopCatRecyclerView() {

        url = url + "?subject_id=" + subject_id;

        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
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
                CourseChapterAdapter adapter=new CourseChapterAdapter(courseChapterList,getApplicationContext());
                recyclerView.setAdapter(adapter);

                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error.networkResponse != null) {
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

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("subject_id", "100001");
//                return params;
//            }
        };
        requestQueue.add(arrayRequest);







//        courseChapterList.add(new CourseChapterModel("Chapter 1 Real Numbers"));
//        courseChapterList.add(new CourseChapterModel("Chapter 2 Polynomials"));
//        courseChapterList.add(new CourseChapterModel("Chapter 3 Pair of Linear Equations in Two Variables"));
//        courseChapterList.add(new CourseChapterModel("Chapter 4 Quadratic Equations"));
//        courseChapterList.add(new CourseChapterModel("Chapter 5 Arithmetic Progressions"));
//        CourseChapterAdapter courseChapterAdapter=new CourseChapterAdapter(courseChapterList,getApplicationContext());
//        recyclerView.setAdapter(courseChapterAdapter);
    }

}