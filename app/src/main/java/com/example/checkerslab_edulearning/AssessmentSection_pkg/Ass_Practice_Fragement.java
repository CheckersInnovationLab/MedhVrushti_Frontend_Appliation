package com.example.checkerslab_edulearning.AssessmentSection_pkg;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Ass_Practice_Fragement extends Fragment {

    private RecyclerView ass_main_courses_recycleView;

    List<Ass_mainCourses_model> ass_mainCourse_list,filterlist;
    Ass_mainCourses_adapter adapter;
    private String Url="http://192.168.50.67:9191/api/v1/cil/board/all";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ass__practice__fragement, container, false);

        ass_main_courses_recycleView=view.findViewById(R.id.Ass_MainCourses_recycleView_id);
        ass_mainCourse_list=new ArrayList<>();
        ass_main_courses_recycleView.setLayoutManager(new LinearLayoutManager(getActivity()));

        RequestQueue requestQueue= Volley.newRequestQueue(getActivity());
        JsonArrayRequest arrayRequest=new JsonArrayRequest(Request.Method.GET, Url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i=0;i<=response.length();i++)
                {
                    try {
                        JSONObject object=response.getJSONObject(i);
                        Ass_mainCourses_model model=new Ass_mainCourses_model(object.getInt("boardId"),
                                object.getString("boardCode"),
                                object.getString("boardName"),
                                 object.getString("boardDescription"),
                                object.getString("boardImgUrl"));
                        ass_mainCourse_list.add(model);
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                adapter=new Ass_mainCourses_adapter(ass_mainCourse_list,getActivity());
                ass_main_courses_recycleView.setAdapter(adapter);

                Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(arrayRequest);

        return view;
    }
}