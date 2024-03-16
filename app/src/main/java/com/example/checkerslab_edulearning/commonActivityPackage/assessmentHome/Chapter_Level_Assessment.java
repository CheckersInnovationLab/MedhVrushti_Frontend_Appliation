package com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.commonActivityPackage.AllAssessmentAdapter;
import com.example.checkerslab_edulearning.commonActivityPackage.AllAssessmentModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class Chapter_Level_Assessment extends Fragment  {

    RecyclerView recyclerView;
    ArrayList<AllAssessmentModel> chapterAssessmentList;
    LinearLayoutManager VerticalLayout;
    AllAssessmentAdapter allAssessmentAdapter;
    int chapAssCount=0;
    private ProgressBar chapterLevelPb;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chapter__level__assessment, container, false);




        Bundle bundle = getArguments();
        String Chapter_id = bundle.getString("mText");
        recyclerView=view.findViewById(R.id.Chapter_Assessment_recyclerView_id);
        chapterLevelPb=view.findViewById(R.id.chapter_level_ass_pbLoading);
        chapterLevelPb.setVisibility(ProgressBar.VISIBLE);
        chapterAssessmentList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);
        getChapterAssessment(Chapter_id);

        return  view;
    }


    private void getChapterAssessment(String chapter_id) {
        String Url= StaticFile.Url+ "/api/v1/cil/assessments/get/all/by/chapter_id/and/user_id?chapter_id="+chapter_id+"&user_id="+StaticFile.userId;
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
                                                object.getString("assessment_id"),
                                                object.getString("assessment_status"));
                                        chapterAssessmentList.add(model);
                                        chapAssCount--;
                                    }

                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        chapterLevelPb.setVisibility(ProgressBar.GONE);
                        allAssessmentAdapter = new AllAssessmentAdapter(chapterAssessmentList, getContext());
                        recyclerView.setAdapter(allAssessmentAdapter);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            chapterLevelPb.setVisibility(ProgressBar.GONE);
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string
                            // Print the error details
                            Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
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

    public interface Backpressedlistener {
        void onBackPressed();
    }

}