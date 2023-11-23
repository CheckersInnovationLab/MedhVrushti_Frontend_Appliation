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

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_learning_main, container, false);
       TabLayout tabLayout = view.findViewById(R.id.tabLayout);
       ViewPager viewPager = view.findViewById(R.id.viewPager);

       TabsPagerAdapter tabsPagerAdapter = new TabsPagerAdapter(getFragmentManager(), 0);
       viewPager.setAdapter(tabsPagerAdapter);
       tabLayout.setupWithViewPager(viewPager);


        return view;
    }

    private void AddItemsToTopCatRecyclerView() {
//        JSONObject requestData = new JSONObject();
//        try {
//            requestData.put("subject_id", "100004");
//            //     requestData.put("lastName", "100002");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }


//
//
//        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
//        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
//            @Override
//            public void onResponse(JSONArray response) {
//                for (int i=0;i<=response.length();i++)
//                {
//                    try {
//                        JSONObject object=response.getJSONObject(i);
//                        MyLeaningMainModel model=new MyLeaningMainModel(object.getString("subscription_id"),
//                                object.getString("subscription_code"),
//                                object.getString("subscription_name"),
//                                object.getString("subscription_type"),
//                                object.getString("subscription_category"),
//                                object.getString("board_id"),
//                                object.getString("description"),
//                                object.getString("subscription_price"),
//                                object.getString("max_discount"),
//                                object.getString("min_discount"),
//                                object.getString("subscription_image_url"),
//                                object.getString("enabled_flag"),
//                                object.getString("std_id"),
//                                object.getString("subject_id"),
//                                object.getString("total_assessment"),
//                                object.getString("attempt_allowed"),
//                                object.getString("subscription_start_date"),
//                                object.getString("access_end_date"),
//                                object.getString("total_validity"),
//                                object.getString("reviews"),
//                                object.getString("ratings"),
//                                object.getString("status"));
//
//
//                        myLearningCoursesList.add(model);
//                    }catch (Exception e)
//                    {
//                        e.printStackTrace();
//                    }
//                }
//                MyLearningMainAdapter myLearningMainAdapter=new MyLearningMainAdapter(myLearningCoursesList,getContext());
//                recyclerView.setAdapter(myLearningMainAdapter);
//
//                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//               // Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        })
//        {
//            @Override
//            protected Map<String, String> getParams() {
//                // below line we are creating a map for
//                // storing our values in key and value pair.
//                Map<String, String> params = new HashMap<String, String>();
//
//                params.put("user_id","100003");
//
//                return params;
//            }
//        };
//
//        requestQueue.add(arrayRequest);

//        myLearningCoursesList.add(new MyLeaningMainModel(url01,"SSC 10th Test series","500"));
//        myLearningCoursesList.add(new MyLeaningMainModel(url02,"HSC 11th Test series","300"));
//        myLearningCoursesList.add(new MyLeaningMainModel(url03,"CBSC 12th Test series","600"));
//        MyLearningMainAdapter myLearningMainAdapter=new MyLearningMainAdapter(myLearningCoursesList,getContext());
//        recyclerView.setAdapter(myLearningMainAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        AddItemsToTopCatRecyclerView();
    }
}