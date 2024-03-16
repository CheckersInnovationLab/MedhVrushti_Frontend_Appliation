package com.example.checkerslab_edulearning.myLearningPakage;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.checkerslab_edulearning.storePackage.StoreAllCoursesScreen;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyLearning_Tab1_Fragment extends Fragment {

    private RecyclerView recyclerView;
    private LinearLayoutManager verticalLayout;
    private MyLearningMainAdapter myLearningMainAdapter;
    private CardView emptyMessageCard;
    private RelativeLayout enrollButton;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_learning__tab1_, container, false);
        emptyMessageCard=view.findViewById(R.id.MyLearning_empty_Message_details2_id);
        enrollButton=view.findViewById(R.id.MyLearning_EnrollButton_id);










        enrollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getContext(), StoreAllCoursesScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });

        recyclerView=view.findViewById(R.id.MyLearningMainRecyclerView_id);
        verticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        //set adapter to recycler view
        recyclerView.setLayoutManager(verticalLayout);

        if (MyLearningMainFragment.myLearningCoursesList.size()>0)
        {
            emptyMessageCard.setVisibility(View.GONE);
            myLearningMainAdapter = new MyLearningMainAdapter(MyLearningMainFragment.myLearningCoursesList, getContext());
            recyclerView.setAdapter(myLearningMainAdapter);
        }
        else {

        }

        return view;

    }

}