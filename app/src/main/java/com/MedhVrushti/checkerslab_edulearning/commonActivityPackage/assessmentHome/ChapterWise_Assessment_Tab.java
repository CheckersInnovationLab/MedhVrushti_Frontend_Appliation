package com.MedhVrushti.checkerslab_edulearning.commonActivityPackage.assessmentHome;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.MedhVrushti.checkerslab_edulearning.R;
import com.MedhVrushti.checkerslab_edulearning.commonActivityPackage.CourseChapterAdapter;

public class ChapterWise_Assessment_Tab extends Fragment {

    RecyclerView recyclerView;
    LinearLayoutManager VerticalLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chapter_wise__assessment__tab, container, false);

        recyclerView=view.findViewById(R.id.Course_chapter_recyclerView_id);
        VerticalLayout
                = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        recyclerView.setLayoutManager(VerticalLayout);


        CourseChapterAdapter adapter=new CourseChapterAdapter(Assessment_home_Screen.courseChapterList,getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }


}
