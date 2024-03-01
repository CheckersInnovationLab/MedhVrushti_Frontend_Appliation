package com.example.checkerslab_edulearning.commonActivityPackage.assessmentHome;

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
import com.example.checkerslab_edulearning.R;
import com.example.checkerslab_edulearning.StaticFile;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseChapterAdapter;
import com.example.checkerslab_edulearning.commonActivityPackage.CourseChapterModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

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

        Log.d("getChapter",String.valueOf(Assessment_home_Screen.courseChapterList.size()));

        CourseChapterAdapter adapter=new CourseChapterAdapter(Assessment_home_Screen.courseChapterList,getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }


}
