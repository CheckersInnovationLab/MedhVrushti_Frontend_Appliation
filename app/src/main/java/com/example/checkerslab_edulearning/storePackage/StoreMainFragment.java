package com.example.checkerslab_edulearning.storePackage;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.mainHome_pkg.PopularCoursesAdapter;
import com.example.checkerslab_edulearning.mainHome_pkg.popularCoursesModel;

import java.util.ArrayList;

public class StoreMainFragment extends Fragment {


    RecyclerView recyclerView1;
    ArrayList<StoreCoursesModel> storeCoursesList;
    LinearLayoutManager HorizontalLayout;

    String url01="https://firebasestorage.googleapis.com/v0/b/iit-foundation.appspot.com/o/All%20Courses%20Image%2Fdemo%2Fimg2.png?alt=media&token=83219b93-cb17-4ee3-998d-131cfdfe5647";
    String url02="https://firebasestorage.googleapis.com/v0/b/iit-foundation.appspot.com/o/All%20Courses%20Image%2Fdemo%2Fimg2.png?alt=media&token=83219b93-cb17-4ee3-998d-131cfdfe5647";
    String url03="https://firebasestorage.googleapis.com/v0/b/iit-foundation.appspot.com/o/All%20Courses%20Image%2Fdemo%2Fimg2.png?alt=media&token=83219b93-cb17-4ee3-998d-131cfdfe5647";



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_main__store_fragment, container, false);


        recyclerView1=view.findViewById(R.id.Store_courses_recycler_id);
        storeCoursesList=new ArrayList<>();
        HorizontalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);

        recyclerView1.setLayoutManager(HorizontalLayout);
        AddItemsToTopCatRecyclerView();

        return view;
    }

    private void AddItemsToTopCatRecyclerView() {


        storeCoursesList.add(new StoreCoursesModel(url01,"SSC 10th Test series","500"));
        storeCoursesList.add(new StoreCoursesModel(url02,"HSC 11th Test series","300"));
        storeCoursesList.add(new StoreCoursesModel(url03,"CBSC 12th Test series","600"));
        StoreCoursesAdapter storeCoursesAdapter=new StoreCoursesAdapter(storeCoursesList,getContext());
        recyclerView1.setAdapter(storeCoursesAdapter);
    }
}