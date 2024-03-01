package com.example.checkerslab_edulearning.myLearningPakage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_Standards_Model;
import com.example.checkerslab_edulearning.AssessmentSection_pkg.Ass_standards_adapter;
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ChildModel;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentAdapter;
import com.example.checkerslab_edulearning.TheoryAssessmentPackage.questionPaperPackage.ParentModel;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesAdapter;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesModel;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MyLearningMainFragment extends Fragment {

    static public ArrayList<MyLeaningMainModel> myLearningCoursesList;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ProgressBar pb;
    private String userSubscriptionUrl= StaticFile.Url+"/api/v1/cil/user_subscriptions/get/all/by/user_id?";

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_my_learning_main, container, false);
       tabLayout = view.findViewById(R.id.tabLayout);
       viewPager = view.findViewById(R.id.viewPager);

       myLearningCoursesList=new ArrayList<>();
       pb = (ProgressBar) view.findViewById(R.id.pbLoading);
       pb.setVisibility(ProgressBar.VISIBLE);
       AddItemsToTopCatRecyclerView();
        return view;
    }

    private void AddItemsToTopCatRecyclerView() {

       Log.d("AddItemsToTopCatRecyclerView","IN");

        userSubscriptionUrl=userSubscriptionUrl+"user_id="+ StaticFile.userId;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());

        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, userSubscriptionUrl,null,
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
                                        object.getString("attribute1")
                                );
                                myLearningCoursesList.add(model);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        pb.setVisibility(ProgressBar.GONE);
                        setTabLayout();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        if (error.networkResponse != null) {
                            pb.setVisibility(ProgressBar.GONE);
                            int statusCode = error.networkResponse.statusCode;
                            byte[] errorResponseData = error.networkResponse.data; // Error response data
                            String errorMessage = new String(errorResponseData); // Convert error data to string

                            //log for error
                            Log.d("userSubscriptionUrl Error Status Code: ", String.valueOf(statusCode));
                            Log.d(" userSubscriptionUrl Error Response Data: ", errorMessage);
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

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void setTabLayout() {
        TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager(), 0);
        viewPager.setAdapter(tabsPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}