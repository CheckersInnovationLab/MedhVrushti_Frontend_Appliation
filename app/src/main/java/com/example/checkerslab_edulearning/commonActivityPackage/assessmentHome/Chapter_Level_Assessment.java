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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Chapter_Level_Assessment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AllAssessmentModel> chapterAssessmentList;
    LinearLayoutManager VerticalLayout;
    AllAssessmentAdapter allAssessmentAdapter;
    int chapAssCount=0;
    private String Url="https://medhvrushti.checkerslab.com/api/v1/cil/assessments/get/all/by/subject_id?subject_id=1001";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chapter__level__assessment, container, false);
        recyclerView=view.findViewById(R.id.Chapter_Assessment_recyclerView_id);
        chapterAssessmentList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);
        getChapterAssessment();
        return  view;
    }

    private void getChapterAssessment() {
        chapAssCount=Assessment_home_Screen.chapterMCQAssessmentCount;

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, Url,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                Toast.makeText(getContext(), object.getString("assessment_category"), Toast.LENGTH_SHORT).show();

                                if (object.getString("assessment_category").equals("CHAPTER ASSESSMENT") ||object.getString("assessment_category")=="CHAPTER ASSESSMENT")
                                {
                                    if (0<chapAssCount)
                                    {
                                        AllAssessmentModel model=new AllAssessmentModel(
                                                object.getString("assessment_name"),
                                                object.getString("total_marks"),
                                                object.getString("assessment_id"));
                                        chapterAssessmentList.add(model);
                                        chapAssCount--;
                                    }

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        allAssessmentAdapter = new AllAssessmentAdapter(chapterAssessmentList, getContext());
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
    }
}