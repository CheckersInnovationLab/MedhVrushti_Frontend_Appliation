package com.MedhVrushti.checkerslab_edulearning.storePackage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.MedhVrushti.checkerslab_edulearning.ErrorStatusDialog;
import com.MedhVrushti.checkerslab_edulearning.LoadingDialog;
import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.StaticFile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StoreAllCoursesScreen extends AppCompatActivity {

     private RecyclerView recyclerView;
     private List<StoreCoursesModel> coursesItemList;
     private ImageView backButton;
     private LoadingDialog loadingDialog;
     private ErrorStatusDialog errorStatusDialog;
      String Url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_all_courses_screen);

        loadingDialog=new LoadingDialog(StoreAllCoursesScreen.this);
        errorStatusDialog=new ErrorStatusDialog(StoreAllCoursesScreen.this);

        recyclerView=findViewById(R.id.AllCourses_RecyclerView_id);
        backButton=findViewById(R.id.allCourses_Toolbar_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        coursesItemList=new ArrayList<>();
        getAllCourses();

    }

    private void getAllCourses() {
        loadingDialog.startLoadingDialog();
       String Url = StaticFile.Url+"/api/v1/cil/main_subscriptions/get/all";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        loadingDialog.dismissDialog();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);


                                StoreCoursesModel storeCoursesModel=new StoreCoursesModel(
                                        object.getString("subscription_id"),
                                        object.getString("subscription_code"),
                                        object.getString("subscription_name"),
                                        object.getString("subscription_type"),
                                        object.getString("subscription_category"),
                                        object.getString("standard_id"),
                                        object.getString("subject_id"),
                                        object.getString("subscription_price"),
                                        object.getString("description"),
                                        object.getString("default_discount"),
                                        object.getString("subscription_img_url"),
                                        object.getString("total_validity"));

                               coursesItemList.add(storeCoursesModel);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        StoreCoursesAdapter storeCoursesAdapter
                                = new StoreCoursesAdapter(coursesItemList,getApplicationContext());
                        recyclerView.setAdapter(storeCoursesAdapter);

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loadingDialog.dismissDialog();
                        errorStatusDialog.showErrorMessage();
                        // Handle error response
                        if (error.networkResponse != null) {
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