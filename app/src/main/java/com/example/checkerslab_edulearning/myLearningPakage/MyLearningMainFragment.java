package com.example.checkerslab_edulearning.myLearningPakage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesAdapter;
import com.example.checkerslab_edulearning.storePackage.StoreCoursesModel;

import java.util.ArrayList;

public class MyLearningMainFragment extends Fragment {


    private  RecyclerView recyclerView;
    ArrayList<MyLeaningMainModel> myLearningCoursesList;
    LinearLayoutManager VerticalLayout;

    String url01="https://firebasestorage.googleapis.com/v0/b/iit-foundation.appspot.com/o/All%20Courses%20Image%2Fdemo%2Fimg2.png?alt=media&token=83219b93-cb17-4ee3-998d-131cfdfe5647";
    String url02="https://firebasestorage.googleapis.com/v0/b/iit-foundation.appspot.com/o/All%20Courses%20Image%2Fdemo%2Fimg2.png?alt=media&token=83219b93-cb17-4ee3-998d-131cfdfe5647";
    String url03="https://firebasestorage.googleapis.com/v0/b/iit-foundation.appspot.com/o/All%20Courses%20Image%2Fdemo%2Fimg2.png?alt=media&token=83219b93-cb17-4ee3-998d-131cfdfe5647";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_my_learning_main, container, false);


        recyclerView=view.findViewById(R.id.MyLearningMainRecyclerView_id);
        myLearningCoursesList=new ArrayList<>();
        VerticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);
        AddItemsToTopCatRecyclerView();
        return view;
    }

    private void AddItemsToTopCatRecyclerView() {



        myLearningCoursesList.add(new MyLeaningMainModel(url01,"SSC 10th Test series","500"));
        myLearningCoursesList.add(new MyLeaningMainModel(url02,"HSC 11th Test series","300"));
        myLearningCoursesList.add(new MyLeaningMainModel(url03,"CBSC 12th Test series","600"));
        MyLearningMainAdapter myLearningMainAdapter=new MyLearningMainAdapter(myLearningCoursesList,getContext());
        recyclerView.setAdapter(myLearningMainAdapter);
    }
}