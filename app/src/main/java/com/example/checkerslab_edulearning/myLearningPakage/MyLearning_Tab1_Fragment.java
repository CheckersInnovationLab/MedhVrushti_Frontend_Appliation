package com.example.checkerslab_edulearning.myLearningPakage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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
import com.example.checkerslab_edulearning.StaticFile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyLearning_Tab1_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLayout;
    private MyLearningMainAdapter myLearningMainAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_learning__tab1_, container, false);

        recyclerView=view.findViewById(R.id.MyLearningMainRecyclerView_id);
        verticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        //set adapter to recycler view
        recyclerView.setLayoutManager(verticalLayout);
        myLearningMainAdapter = new MyLearningMainAdapter(MyLearningMainFragment.myLearningCoursesList, getContext());
        recyclerView.setAdapter(myLearningMainAdapter);

        return view;

    }

}