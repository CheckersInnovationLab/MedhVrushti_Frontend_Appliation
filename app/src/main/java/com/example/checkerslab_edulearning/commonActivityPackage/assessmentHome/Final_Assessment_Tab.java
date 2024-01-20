package com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome;

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
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.commonActivityPackage.AllAssessmentAdapter;
import com.example.checkerslab_edulearning.commonActivityPackage.AllAssessmentModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLeaningMainModel;
import com.example.checkerslab_edulearning.myLearningPakage.MyLearningMainAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Final_Assessment_Tab extends Fragment {



    RecyclerView recyclerView;
    ArrayList<AllAssessmentModel> assessmentList;
    LinearLayoutManager VerticalLayout;
    AllAssessmentAdapter allAssessmentAdapter;
    private String Url="https://medhvrushti.checkerslab.com/api/v1/cil/assessments/get/all/by/subject_id?subject_id=1001";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_final__assessment__tab, container, false);


        recyclerView=view.findViewById(R.id.all_assessment_recyclerView_id);
        assessmentList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);
        AddItemsToTopCatRecyclerView();
        return  view;
    }


    private void AddItemsToTopCatRecyclerView() {

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                AllAssessmentModel model=new AllAssessmentModel(
                                        object.getString("assessment_name"),
                                        object.getString("total_marks"),
                                        object.getString("assessment_id"));
                                assessmentList.add(model);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        allAssessmentAdapter = new AllAssessmentAdapter(assessmentList, getContext());
                        recyclerView.setAdapter(allAssessmentAdapter);
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

//        assessmentList.add(new AllAssessmentModel("Chapter 1 Real Numbers","30"));
//        assessmentList.add(new AllAssessmentModel("Chapter 2 Polynomials","70"));
//        assessmentList.add(new AllAssessmentModel("Chapter 3 Pair of Linear Equations in Two Variables","70"));
//        assessmentList.add(new AllAssessmentModel("Chapter 4 Quadratic Equations","70"));
//        assessmentList.add(new AllAssessmentModel("Chapter 5 Arithmetic Progressions","70"));
//        AllAssessmentAdapter allAssessmentAdapter=new AllAssessmentAdapter(assessmentList,getContext());
//        recyclerView.setAdapter(allAssessmentAdapter);
    }
}