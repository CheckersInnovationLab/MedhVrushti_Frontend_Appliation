package com.example.checkerslab_edulearning.myLearningPakage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.Navigation_Drawer_Activity;
import com.example.checkerslab_edulearning.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyLearning_Tab1_Fragment extends Fragment {

    private RecyclerView recyclerView;
    ArrayList<MyLeaningMainModel> myLearningCoursesList;
    LinearLayoutManager VerticalLayout;
    MyLearningMainAdapter myLearningMainAdapter;

    private String Url="https://medhvrushti.checkerslab.com/api/v1/cil/user_subscriptions/get/all/by/user_id?";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_learning__tab1_, container, false);


        recyclerView=view.findViewById(R.id.MyLearningMainRecyclerView_id);
        myLearningCoursesList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);
        AddItemsToTopCatRecyclerView();
        Toast.makeText(getContext(), "Tab1 open ", Toast.LENGTH_SHORT).show();


        return view;
    }

    private void AddItemsToTopCatRecyclerView() {

        Url=Url+"user_id="+ Navigation_Drawer_Activity.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                MyLeaningMainModel model=new MyLeaningMainModel(object.getString("user_subscription_id"),
                                        object.getString("user_id"),
                                        object.getString("subscription_id"),
                                        object.getString("standard_id"),
                                        object.getString("subscription_name"),
                                        object.getString("subscription_type"),
                                        object.getString("subscription_category"),
                                        object.getString("description"),
                                        object.getString("subscription_image"),
                                        object.getString("subscription_date"),
                                        object.getString("access_end_date"),
                                        object.getString("attribute1"));
                              myLearningCoursesList.add(model);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        myLearningMainAdapter = new MyLearningMainAdapter(myLearningCoursesList, getContext());
                        recyclerView.setAdapter(myLearningMainAdapter);
                        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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