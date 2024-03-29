package com.MedhVrushti.checkerslab_edulearning.commonActivityPackage.assessmentHome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.commonActivityPackage.AllAssessmentAdapter;
import com.MedhVrushti.checkerslab_edulearning.commonActivityPackage.AllAssessmentModel;

import java.util.ArrayList;

public class Final_Assessment_Tab extends Fragment {

    RecyclerView recyclerView;
    ArrayList<AllAssessmentModel> assessmentList;
    LinearLayoutManager VerticalLayout;
    AllAssessmentAdapter allAssessmentAdapter;

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
         allAssessmentAdapter = new AllAssessmentAdapter(Assessment_home_Screen.finalAssessmentList, getContext());
         recyclerView.setAdapter(allAssessmentAdapter);

        return  view;
    }

}