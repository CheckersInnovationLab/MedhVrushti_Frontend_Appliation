package com.MedhVrushti.checkerslab_edulearning.NavigationDrawerPkg;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.MedhVrushti.checkerslab_edulearning.myLearningPakage.MyLearningMainFragment;
import com.MedhVrushti.checkerslab_edulearning.myLearningPakage.MyLearning_Tab1_Fragment;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.StaticFile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class PerformanceFragment extends Fragment {



    RecyclerView recyclerView;
    ArrayList<myAssessmentModel> myAssessmentList;
    LinearLayoutManager VerticalLayout;
    myAssessmentAdapter myAssessmentAdapter;
    private CardView emptyMsg;
    private RelativeLayout startButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragment_performance, container, false);

        recyclerView=view.findViewById(R.id.Assessment_history_recycler_id);
        emptyMsg=view.findViewById(R.id.Performance_empty_Message_details2_id);
        startButton=view.findViewById(R.id.Performance_startButton_id);
        myAssessmentList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);


        getAssessmentHistoryData();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.layout_id, new MyLearningMainFragment());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

    private void getAssessmentHistoryData() {

        String url2= StaticFile.Url+ "/api/v1/cil/user_assessments/get/all/by/user_id/custom?user_id="+StaticFile.userId;

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url2,null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        // Handle success response from the server
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject object = response.getJSONObject(i);
                                myAssessmentModel model=new myAssessmentModel(
                                        object.getString("user_ass_id"),
                                        object.getString("assessment_name"),
                                        object.getString("result_status"),
                                        object.getString("ass_end_date"),
                                        object.getString("obtained_marks"),
                                        object.getString("total_marks"),
                                        object.getString("assessment_id")
                                );
                                myAssessmentList.add(model);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("myAssessmentList",String.valueOf(myAssessmentList.size()));
                        if (myAssessmentList.size()>0)
                        {
                            Log.d("myAssessmentList111",String.valueOf(myAssessmentList.size()));
                            emptyMsg.setVisibility(View.GONE);
                            myAssessmentAdapter = new myAssessmentAdapter(myAssessmentList, getContext());
                            recyclerView.setAdapter(myAssessmentAdapter);
                            Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            emptyMsg.setVisibility(View.VISIBLE);
                        }

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